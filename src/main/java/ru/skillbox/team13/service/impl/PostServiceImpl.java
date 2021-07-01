package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.entity.projection.LikeCount;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.FeedMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.repository.QueryDSL.PostDAO;
import ru.skillbox.team13.util.TimeUtil;

import java.util.List;

import static ru.skillbox.team13.util.PageUtil.getPageable;
import static ru.skillbox.team13.util.TimeUtil.getTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements ru.skillbox.team13.service.PostService {

    private final PostDAO postDAO;
    private final PostRepository postRepository;
    private final DumbFeedsService feedsService;
    private final CommentRepository commentRepository;

    @Override
    public DTOWrapper find(String text, Long timestampFrom, Long timestampTo, int offset, int itemPerPage) {
        //posts with authors
        Page<Post> page = postDAO.findByTextAndTime(text, getTime(timestampFrom), getTime(timestampTo), getPageable(offset, itemPerPage));

        //todo trashy, refactor
        //likes for posts
        List<LikeCount> likes = postRepository.countLikesByPosts(page.getContent());

        //for post get comments
        List<CommentProjection> comments = feedsService.getCommentProjections(page.getContent());

        //build feed
        List<PostDto> feed = FeedMapper.buildFeed(page.getContent(), likes, comments);

        return WrapperMapper.wrap(feed, (int) page.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    public DTOWrapper getById(int id) {

        //todo refactor
        Post p = postRepository.findById(id).orElseThrow(() -> new BadRequestException("no post for id=" + id));
        LikeCount likes = postRepository.countLikesByPosts(p);
        List<CommentProjection> comments = commentRepository.getCommProjections(p);
        PostDto dto = FeedMapper.buildPostDto(p, List.of(likes), comments);
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
    public DTOWrapper deleteById(int id) {
        return null;
    }

    @Override
    public DTOWrapper recoverById(int id) {
        return null;
    }
}
