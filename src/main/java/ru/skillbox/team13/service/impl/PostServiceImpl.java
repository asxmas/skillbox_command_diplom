package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.repository.QueryDSL.CommentDAO;
import ru.skillbox.team13.repository.QueryDSL.PersonDAO;
import ru.skillbox.team13.repository.QueryDSL.PostDAO;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.*;
import static ru.skillbox.team13.util.PageUtil.getPageable;
import static ru.skillbox.team13.util.TimeUtil.getTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements ru.skillbox.team13.service.PostService {

    private final PostDAO postDAO;
    private final PostRepository postRepository; //todo move to PostDAO
    private final PersonRepository personRepository; //todo move usage to PersonDAO
    private final PersonDAO personDAO;
    private final CommentDAO commentDAO;
    private final UserService userService;

    @Override
    public DTOWrapper getFeed(String substr, int offset, int itemPerpage) {

        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        List<PersonDTO> personDTOS = personDAO.fetchFriendDtos(currentPersonId, FriendshipStatusCode.FRIEND);

        List<Integer> authorIds = personDTOS.stream().map(PersonDTO::getId).collect(toList());
        Page<PostDto> postDtoPage = postDAO.getPostDtosByAuthorIdAndSubstring(authorIds, substr, getPageable(offset, itemPerpage));

        List<Integer> postIds = postDtoPage.getContent().stream().map(PostDto::getId).collect(toList());
        List<CommentDto> commentDtos = commentDAO.getCommentDtosForPostIds(postIds);

        List<PostDto> combined = combine(postDtoPage.getContent(), personDTOS, commentDtos);
        log.debug("Loading feed page {}, total {} posts and {} comments.", offset / itemPerpage, combined.size(), commentDtos.size());

        return WrapperMapper.wrap(combined, (int) postDtoPage.getTotalElements(), offset, itemPerpage, true);
    }

    @Override
    public DTOWrapper find(String text, Long timestampFrom, Long timestampTo, int offset, int itemPerPage) {
        Page<PostDto> page = postDAO.getPostsDtosByTimeAndSubstring(text, getTime(timestampFrom), getTime(timestampTo),
                getPageable(offset, itemPerPage));

        List<Integer> personIds = page.getContent().stream().map(p -> p.getAuthor().getId()).collect(toList());
        List<PersonDTO> personDtos = personDAO.getById(personIds);

        List<CommentDto> commentDtos = commentDAO.getCommentDtosForPostIds(personIds);

        List<PostDto> combined = combine(page.getContent(), personDtos, commentDtos);
        log.debug("Loading search page {}, total {} posts and {} comments.", offset / itemPerPage, combined.size(), commentDtos.size());

        return WrapperMapper.wrap(combined, (int) page.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    public DTOWrapper getById(int id) {
        PostDto postDto = postDAO.getSingleDtoById(id);
        int authorId = postDto.getAuthor().getId();
        PersonDTO personDTO = personDAO.getById(authorId);
        List<CommentDto> commentDtos = commentDAO.getCommentDtosForPostIds(id);
        combine(postDto, personDTO, commentDtos);
        log.debug("Loading post id={}, with {} comments.", id, commentDtos.size());

        return WrapperMapper.wrap(postDto, true);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper edit(int id, Long pubDate, String title, String text) {
        LocalDateTime ldt = TimeUtil.getTime(pubDate);
        postDAO.edit(id, ldt, title, text);
        log.debug("Editing post id={} with date={}, title={} and text length={}", id, ldt, title, text);

        return getById(id);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper deleteById(int id) {
        postDAO.delete(id, true);
        log.debug("Deleting post id={}", id);

        return WrapperMapper.wrap(Map.of("id", id), true);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper recoverById(int id) {
        postDAO.delete(id, false);
        log.debug("Un-deleting post id={}", id);

        return getById(id);
    }

    @Override
    public DTOWrapper getWallForUserId(int authorId, int offset, int itemPerPage) {
        PersonDTO personDto = personDAO.getById(authorId);
        Page<PostDto> userPosts = postDAO.getPostDtosByAuthorIdAndSubstring(List.of(authorId),
                null, getPageable(offset, itemPerPage));

        List<Integer> postIds = userPosts.getContent().stream().map(PostDto::getId).collect(toList());
        List<CommentDto> commentDtos = commentDAO.getCommentDtosForPostIds(postIds);

        List<PostDto> combined = combine(userPosts.getContent(), List.of(personDto), commentDtos);
        log.debug("Loading wall for user id={} page {}, total {} posts and {} comments.",
                authorId, offset / itemPerPage, combined.size(), commentDtos.size());

        return WrapperMapper.wrap(combined, (int) userPosts.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    @Transactional
    public DTOWrapper post(String title, String text, Integer authorId, Long pubDate) {
        Person author = personRepository.findById(authorId)
                .orElseThrow(() -> new BadRequestException("No person for id=" + authorId + " is found."));
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(title);
        post.setPostText(text);
        post.setDeleted(false);
        post.setBlocked(false);
        post.setTime(Objects.requireNonNullElse(TimeUtil.getTime(pubDate), LocalDateTime.now()));
        int postId = postRepository.save(post).getId();

        return getById(postId);
    }

    private List<PostDto> combine(List<PostDto> posts, List<PersonDTO> persons, List<CommentDto> comments) {
        Map<Integer, PersonDTO> personMap = persons.stream().collect(toMap(PersonDTO::getId, p -> p));
        Map<Integer, List<CommentDto>> commentMap = comments.stream().collect(groupingBy(CommentDto::getPostId));

        for (PostDto p : posts) {
            p.setAuthor(personMap.get(p.getAuthor().getId()));
            p.setComments(Objects.requireNonNullElse(commentMap.get(p.getId()), Collections.emptyList()));
        }
        return posts;
    }

    private void combine(PostDto post, PersonDTO person, List<CommentDto> comments) {
        post.setAuthor(person);
        post.setComments(comments);
    }
}
