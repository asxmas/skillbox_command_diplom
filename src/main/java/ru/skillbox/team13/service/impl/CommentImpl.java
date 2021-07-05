package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CommentToPostDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.mapper.FeedMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentImpl implements CommentService {

    private final CommentRepository commRep;
    private final PostRepository postRep;

    @Override
    public DTOWrapper getAllCommentByPost(int id, int offset, int itemPerPage) {
        Post post = postRep.getById(id);
        List<CommentProjection> commentByPost = commRep.getCommProjections(post);
        return WrapperMapper.wrap(FeedMapper.getCommentsForPostId(id, commentByPost),
                FeedMapper.getCommentsForPostId(id, commentByPost).size(),
                offset,
                itemPerPage,
                true);
    }

    @Override
    @Transactional
    public DTOWrapper setCommentToPost(CommentToPostDto commentToPostDto, int id) {
       Post post = postRep.getById(id);
        return null;
    }

    @Override
    public DTOWrapper editCommentById() {
        return null;
    }

    @Override
    public DTOWrapper deleteCommentById() {
        return null;
    }

    @Override
    public DTOWrapper restoreCommentById() {
        return null;
    }

    @Override
    public DTOWrapper reportToPost() {
        return null;
    }

    @Override
    public DTOWrapper reportToComment() {
        return null;
    }


}
