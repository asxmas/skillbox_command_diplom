package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.Tag;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.repository.QueryDSL.CommentDAO;
import ru.skillbox.team13.repository.QueryDSL.PersonDAO;
import ru.skillbox.team13.repository.QueryDSL.PostDAO;
import ru.skillbox.team13.service.TagService;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.CommentUtil;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static ru.skillbox.team13.util.PageUtil.getPageable;
import static ru.skillbox.team13.util.TimeUtil.getTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements ru.skillbox.team13.service.PostService {

    private final PostDAO postDAO;
    private final PostRepository postRepository; //todo move to PostDAO
    private final PersonRepository personRepository; //todo move usage to PersonDAO
    private final PersonDAO personDAO;
    private final CommentDAO commentDAO;
    private final UserService userService;
    private final TagService tagService;

    @Override
    public DTOWrapper getFeed(String substr, int offset, int itemPerpage) {

        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        List<Integer> authorIds = personDAO.getFriendsIds(currentPersonId, FriendshipStatusCode.FRIEND);

        Page<PostDto> postDtoPage = postDAO.getPostDTOs(currentPersonId, authorIds, null, getPageable(offset, itemPerpage));

        List<Integer> postIds = postDtoPage.getContent().stream().map(PostDto::getId).collect(toList());
        List<CommentDto> commentDtos = commentDAO.getCommentDTOs(currentPersonId, postIds);

        Map<Integer, Set<String>> tagMap = tagService.getPostIdTagsMap(postIds);

        List<PostDto> combined = combine(postDtoPage.getContent(), commentDtos, tagMap);
        log.debug("Loading feed page {}, total {} posts and {} comments.", offset / itemPerpage, combined.size(), commentDtos.size());

        return WrapperMapper.wrap(combined, (int) postDtoPage.getTotalElements(), offset, itemPerpage, true);
    }

    @Override
    public DTOWrapper find(String text, Long timestampFrom, Long timestampTo, int offset, int itemPerPage) {
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        Page<PostDto> page = postDAO.getPostDTOs(currentPersonId, text, getTime(timestampFrom), getTime(timestampTo),
                getPageable(offset, itemPerPage));

        List<Integer> postIds = page.getContent().stream().map(PostDto::getId).collect(toList());

        List<CommentDto> commentDtos = commentDAO.getCommentDTOs(currentPersonId, postIds);

        Map<Integer, Set<String>> tagMap = tagService.getPostIdTagsMap(postIds);

        List<PostDto> combined = combine(page.getContent(), commentDtos, tagMap);
        log.debug("Loading search page {}, total {} posts and {} comments.", offset / itemPerPage, combined.size(), commentDtos.size());

        return WrapperMapper.wrap(combined, (int) page.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    public DTOWrapper getWallForUserId(int authorId, int offset, int itemPerPage) {
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        Page<PostDto> userPosts = postDAO.getPostDTOs(currentPersonId, List.of(authorId),
                null, getPageable(offset, itemPerPage));

        List<Integer> postIds = userPosts.getContent().stream().map(PostDto::getId).collect(toList());
        List<CommentDto> commentDtos = commentDAO.getCommentDTOs(currentPersonId, postIds);

        Map<Integer, Set<String>> tagMap = tagService.getPostIdTagsMap(postIds);

        List<PostDto> combined = combine(userPosts.getContent(), commentDtos, tagMap);
        log.debug("Loading wall for user id={} page {}, total {} posts and {} comments.",
                authorId, offset / itemPerPage, combined.size(), commentDtos.size());

        return WrapperMapper.wrap(combined, (int) userPosts.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    public DTOWrapper getById(int id) {
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        PostDto postDto = postDAO.getPostDTO(currentPersonId, id);
        List<CommentDto> commentDtos = commentDAO.getCommentDTOs(currentPersonId, Collections.singletonList(id));
        Map<Integer, Set<String>> tagMap = tagService.getPostIdTagsMap(Collections.singletonList(id));
        combine(List.of(postDto), commentDtos, tagMap);
        log.debug("Loading post id={}, with {} comments.", id, commentDtos.size());

        return WrapperMapper.wrap(postDto, true);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper edit(int id, Long pubDate, String title, Set<String> tagNames, String text) {
        LocalDateTime ldt = TimeUtil.getTime(pubDate);

        Set<Tag> tags = tagNames.isEmpty() ? new HashSet<>() : tagService.getTagsByName(tagNames);

        postDAO.edit(id, ldt, title, text, tags);
        log.debug("Editing post id={} with date={}, title={} and text length={}", id, ldt, title, text);

//        return getById(id);
        return WrapperMapper.wrapMessage(new MessageDTO("OK id=" + id + " title=" + title + " text=" + text));
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper deleteById(int id) {
        postDAO.delete(id, true);
        log.debug("Deleting post id={}", id);

//        return getById(postId);
        return WrapperMapper.wrapMessage(new MessageDTO("OK id=" + id));
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper recoverById(int id) {
        postDAO.delete(id, false);
        log.debug("Un-deleting post id={}", id);

//        return getById(postId);
        return WrapperMapper.wrapMessage(new MessageDTO("OK id=" + id));
    }

    @Override
    @Transactional
    public DTOWrapper post(String title, String text, Set<String> tagNames, Integer authorId, Long pubDate) {
        Person author = personRepository.findById(authorId)
                .orElseThrow(() -> new BadRequestException("No person for id=" + authorId + " is found."));
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(title);
        post.setPostText(text);
        post.setDeleted(false);
        post.setBlocked(false);
        post.setTime(Objects.requireNonNullElse(TimeUtil.getTime(pubDate), LocalDateTime.now()));

        if (!tagNames.isEmpty()) {
            Set<Tag> tags = tagService.getTagsByName(tagNames);
            post.setTags(tags);
        }
        log.debug("Saving new post (author id={}, title='{}', tags ={})", authorId, title, tagNames);
        int postId = postRepository.save(post).getId();

//        return getById(postId);
        return WrapperMapper.wrapMessage(new MessageDTO("OK id=" + postId + " title=" + title + " text=" + text));
    }

    private List<PostDto> combine(List<PostDto> posts, List<CommentDto> comments, Map<Integer, Set<String>> postIdTagMap) {
        List<CommentDto> sortedComments = CommentUtil.twoLevelCommentSort(comments);

        Map<Integer, PostDto> postDtoMap = posts.stream().collect(Collectors.toMap(PostDto::getId, p -> p));

        for (CommentDto comment : sortedComments) {
            if (postDtoMap.containsKey(comment.getPostId())) { //this check may be unnecessary
                postDtoMap.get(comment.getPostId()).getComments().add(comment);
            }
        }

        for (PostDto post : posts) {
            if (postIdTagMap.containsKey(post.getId())) {
                post.setTags(postIdTagMap.get(post.getId()));
            }
        }

        return posts;
    }

    @Override
    public DTOWrapper deletePostsForAuthor(int authorId) {
        Person currentPerson = userService.getAuthorizedUser().getPerson();
        Set<Post> posts = postRepository.findAllByAuthor(currentPerson);
        log.debug("Deleting {} posts.", posts.size());
        posts.forEach(p -> p.setDeleted(true));
        return WrapperMapper.wrapMessage("OK");
    }

    @Modifying
    @Deprecated
    public void setInactiveAuthor() {
        User currentUser = userService.getAuthorizedUser();
        Person currentPerson = currentUser.getPerson();
        Person inactiveAuthor = userService.getInactivePerson();
        List<Post> posts = postRepository.getPostsByAuthorId(PageRequest.of(0, 10), currentPerson.getId());
        for (Post post : posts) {
            post.setAuthor(inactiveAuthor);
        }
        postRepository.saveAllAndFlush(posts);
    }
}
