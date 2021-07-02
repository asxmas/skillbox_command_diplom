package ru.skillbox.team13.service;

import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.CommentProjection;

import java.util.List;

public interface CommentService {
    List<CommentProjection> getCommentProjections(List<Post> posts);

    List<CommentProjection> getCommentProjections(Post post);
}
