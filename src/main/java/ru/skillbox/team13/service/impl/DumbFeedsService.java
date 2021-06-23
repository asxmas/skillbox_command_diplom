package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.entity.projection.LikeCount;
import ru.skillbox.team13.mapper.FeedMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.service.FriendsService;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.PageUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DumbFeedsService implements ru.skillbox.team13.service.FeedsService {

    private final UserService userService;
    private final FriendsService friendsService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public DTOWrapper serve(String searchSubstring, int offset, int itemPerPage) {
        //todo temporarily made for searchSubstring as '' (empty). add substring search

        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        //get friends (type 'FRIEND')
        List<Person> friends = friendsService.getFriends(currentPersonId, FriendshipStatusCode.FRIEND);

        //count posts
        Integer count = postRepository.countAllByAuthorIn(friends);

        //load posts with persons
        List<Post> posts = getPosts(friends, PageUtil.getPageable(offset, itemPerPage));

        // get likes for posts collection
        List<LikeCount> likes = postRepository.countLikesByPosts(posts);

        //for post get comments
        List<CommentProjection> comments = getCommentProjections(posts);

        //build feed
        List<PostDto> feed = FeedMapper.buildFeed(posts, likes, comments);

        //return
        return WrapperMapper.wrap(feed, count, offset, itemPerPage, true);
    }

    public List<Post> getPosts(List<Person> persons, Pageable p) { //todo set all to private
        return postRepository.findPostsAndAuthorsFromAuthors(p, persons);
    }

    public List<CommentProjection> getCommentProjections(List<Post> posts) {
        List<CommentProjection> comments = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            comments.addAll(commentRepository.getCommProjections(posts.get(i)));         //todo too expensive
        }
        return comments;
    }
}
