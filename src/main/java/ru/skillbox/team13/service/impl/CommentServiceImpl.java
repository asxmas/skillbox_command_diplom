package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.QueryDSL.CommentDAO;
import ru.skillbox.team13.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;

    @Override
    public List<CommentDto> getCommentDtos(List<Post> posts) {
        return commentDAO.getComments(posts.stream().map(Post::getId).collect(Collectors.toList()));
    }

    public List<CommentDto> getCommentDtos(int id) {
        return commentDAO.getComments(id);
    }
}
