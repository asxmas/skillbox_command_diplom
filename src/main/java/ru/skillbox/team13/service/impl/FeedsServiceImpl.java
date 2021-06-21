package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.feeddto.FeedAuthor;
import ru.skillbox.team13.dto.feeddto.FeedCity;
import ru.skillbox.team13.dto.feeddto.FeedCountry;
import ru.skillbox.team13.dto.feeddto.FeedPost;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.entity.projection.LikeCount;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.service.FriendsService;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.PageUtil;
import ru.skillbox.team13.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedsServiceImpl implements ru.skillbox.team13.service.FeedsService {

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

        //get posts for all friends
        Pageable p = PageUtil.getPageable(offset, itemPerPage);

        //load posts with persons
        List<Post> posts = getPosts(friends, p);

        // for post get likes
        List<Integer> likeCounts = postRepository.countLikesByPosts(posts).stream().map(LikeCount::getLikeCount).collect(Collectors.toList());

        //build everything but comments section
        List<FeedPost> feed = preBuildDtos(posts, likeCounts);

        //for post get comments
        for (int i = 0; i < posts.size(); i++) {
            List<CommentProjection> projections = fetchComments(posts.get(i));
        }

        //return
        return WrapperMapper.wrap(feed, count, offset, itemPerPage, true);
    }

    public List<Post> getPosts(List<Person> persons, Pageable p) { //todo set all to private
        return postRepository.findPostsAndAuthorsFromAuthors(p, persons);
    }

    private List<FeedPost> preBuildDtos(List<Post> posts, List<Integer> likeCounts) {
        List<FeedPost> feedPosts = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            Post p = posts.get(i);
            Person pr = p.getAuthor();
            FeedCity fc = new FeedCity(pr.getCity().getId(), pr.getCity().getTitle());
            FeedCountry fco = new FeedCountry(pr.getCountry().getId(), pr.getCountry().getTitle());
            FeedAuthor fa = new FeedAuthor(pr.getId(), pr.getFirstName(), pr.getLastName(), TimeUtil.getTimestamp(pr.getRegDate()),
                    TimeUtil.getTimestamp(pr.getBirthDate()), pr.getEmail(), pr.getPhone(), pr.getPhoto(), pr.getAbout(),
                    fc, fco, pr.getMessagesPermission().name(), TimeUtil.getTimestamp(pr.getLastOnlineTime()), pr.isBlocked());

            FeedPost fp = new FeedPost(p.getId(), TimeUtil.getTimestamp(p.getTime()), fa, p.getTitle(), p.getPostText(),
                    p.isBlocked(), likeCounts.get(i), null);

            feedPosts.add(fp);
        }
        return feedPosts;
    }

    public List<CommentProjection> fetchComments(Post post) {
        return commentRepository.getCommProjections(post);
    }
}
