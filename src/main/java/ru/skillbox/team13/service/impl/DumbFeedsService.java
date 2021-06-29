package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
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

import java.util.ArrayList;
import java.util.List;

import static ru.skillbox.team13.util.PageUtil.getPageable;

@Service
@RequiredArgsConstructor
public class DumbFeedsService implements ru.skillbox.team13.service.FeedsService {

    private final UserService userService;
    private final FriendsService friendsService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public DTOWrapper serve(String searchSubstr, int offset, int itemPerPage) {
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

        // get likes for posts collection
        List<LikeCount> likes = postRepository.countLikesByPosts(posts);

        //for post get comments
        List<CommentProjection> comments = getCommentProjections(posts);

        //build feed
        List<PostDto> feed = FeedMapper.buildFeed(posts, likes, comments);

        //return
        return WrapperMapper.wrap(feed, count, offset, itemPerPage, true);
    }

    public List<Post> getPosts(List<Person> persons, Pageable p) {
        return postRepository.findPostsAndAuthorsFromAuthors(p, persons);
    }

    public List<Post> getPosts(List<Person> persons, Pageable p, String query) {
        return postRepository.findPostsAndAuthorsFromAuthors(p, persons, getQuery(query));
    }

    public List<CommentProjection> getCommentProjections(List<Post> posts) {
        List<CommentProjection> comments = new ArrayList<>();
        for (Post post : posts) {
            comments.addAll(commentRepository.getCommProjections(post));         //todo too expensive
        }
        return comments;
    }

    private String getQuery(String query) {
        return "%" + query.toLowerCase() + "%";
    }
}
