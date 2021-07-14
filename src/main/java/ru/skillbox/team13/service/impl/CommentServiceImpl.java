package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CommentToPostDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.mapper.CommentMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commRep;
    private final PostRepository postRep;
    private final UserServiceImpl userService;

    @Override
    public DTOWrapper getAllCommentByPost(int id, int offset, int itemPerPage) {
        Post post = postRep.getById(id);
        List<CommentProjection> commentByPost = commRep.getCommProjections(post);
        return WrapperMapper.wrap(CommentMapper.getCommentsForPostId(id, commentByPost),
                CommentMapper.getCommentsForPostId(id, commentByPost).size(),
                offset,
                itemPerPage,
                true);
    }

    @Override
    @Transactional
    public DTOWrapper setCommentToPost(CommentToPostDto commentToPostDto, int id) {
        Post post = postRep.getById(id);
        Comment comment = CommentMapper.mapToCommentEntity(commentToPostDto,
                post,
                commRep,
                userService);
        commRep.save(comment);

        //ToDoo make create notification
        return WrapperMapper.wrap();
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
