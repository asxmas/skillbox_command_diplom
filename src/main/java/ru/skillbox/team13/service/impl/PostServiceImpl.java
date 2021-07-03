package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.entity.projection.LikeCount;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.PostMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.repository.QueryDSL.PostDAO;
import ru.skillbox.team13.service.CommentService;
import ru.skillbox.team13.service.FriendsService;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.TimeUtil;

import java.util.List;
import java.util.Map;

import static ru.skillbox.team13.util.PageUtil.getPageable;
import static ru.skillbox.team13.util.TimeUtil.getTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements ru.skillbox.team13.service.PostService {

    private final PostDAO postDAO;
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final FriendsService friendsService;

    @Override
    public DTOWrapper getFeed(String searchSubstr, int offset, int itemPerPage) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        //get friends (type 'FRIEND')
        List<Person> friends = friendsService.getFriends(currentPersonId, FriendshipStatusCode.FRIEND);

        //count posts
        Integer count = searchSubstr.isBlank() ?
                postRepository.countAllByAuthorIn(friends) :
                postRepository.countAllByAuthorInAndTitleLikeOrPostTextLike(friends,
                        getQuery(searchSubstr), getQuery(searchSubstr));

        //load posts with persons
        List<Post> posts = searchSubstr.isBlank() ?
                getPosts(friends, getPageable(offset, itemPerPage)) :
                getPosts(friends, getPageable(offset, itemPerPage), searchSubstr);

        List<LikeCount> likes = postRepository.countLikesByPosts(posts);
        List<CommentDto> comments = commentService.getCommentDtos(posts);
        List<PostDto> feed = PostMapper.combinePostsLikesComments(posts, likes, comments);

        return WrapperMapper.wrap(feed, count, offset, itemPerPage, true);
    }

    @Override
    public DTOWrapper find(String text, Long timestampFrom, Long timestampTo, int offset, int itemPerPage) {
        Page<Post> page = postDAO.findByTextAndTime(text, getTime(timestampFrom), getTime(timestampTo), getPageable(offset, itemPerPage));

        List<LikeCount> likes = postRepository.countLikesByPosts(page.getContent());
        List<CommentDto> comments = commentService.getCommentDtos(page.getContent());
        List<PostDto> posts = PostMapper.combinePostsLikesComments(page.getContent(), likes, comments);

        return WrapperMapper.wrap(posts, (int) page.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    public DTOWrapper getById(int id) {

        //todo refactor
        Post p = postRepository.findById(id).orElseThrow(() -> new BadRequestException("no post for id=" + id));
        LikeCount likes = postRepository.countLikesByPosts(p);
        List<CommentDto> comments = commentService.getCommentDtos(List.of(p));
        PostDto dto = PostMapper.buildPostDto(p, List.of(likes), comments);
        return WrapperMapper.wrap(dto, true);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper edit(int id, Long pubDate, String title, String text) {
        Post p = postRepository.findById(id).orElseThrow(() -> new BadRequestException("no post for id=" + id));
        if (pubDate != null) p.setTime(TimeUtil.getTime(pubDate));
        p.setTitle(title);
        p.setPostText(text);
        postRepository.save(p);
        return getById(id);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper deleteById(int id) {
        Post p = postRepository.findById(id).orElseThrow(() -> new BadRequestException("no post for id=" + id));
        p.setDeleted(true);
        postRepository.save(p);
        return WrapperMapper.wrap(Map.of("id", id), true);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper recoverById(int id) {
        Post p = postRepository.findByIdAndDeleted(id, true).orElseThrow(() -> new BadRequestException("no post for id=" + id));
        p.setDeleted(false);
        postRepository.save(p);
        return getById(id);
    }

    public List<Post> getPosts(List<Person> persons, Pageable p) {
        return postRepository.findPostsAndAuthorsFromAuthors(p, persons);
    }

    public List<Post> getPosts(List<Person> persons, Pageable p, String query) {
        return postRepository.findPostsAndAuthorsFromAuthors(p, persons, getQuery(query));
    }

    private String getQuery(String query) {
        return "%" + query.toLowerCase() + "%";
    }
}
