package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Post;

import java.util.List;

public interface CommentService {
    @Deprecated
    List<CommentDto> getCommentDtos(List<Post> postIds);

    List<CommentDto> getCommentDtos(int id);
}
