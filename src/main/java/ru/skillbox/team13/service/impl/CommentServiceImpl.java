package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.service.CommentService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    @Override
    public List<CommentProjection> getCommentProjections(List<Post> posts) {
        List<CommentProjection> comments = new ArrayList<>();
        for (Post post : posts) {
            comments.addAll(getCommentProjections(post));         //todo too expensive
        }
        return comments;
    }

    @Override
    public List<CommentProjection> getCommentProjections(Post post) {
        return commentRepository.getCommProjections(post);
    }
}
