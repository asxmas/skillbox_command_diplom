package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.CommentDTO;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.service.CommentService;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommentMapper {
    private static CommentService commentService;
    public static CommentDTO convertCommentToCommentDTO(Comment comment)    {
        return CommentDTO.builder()
                .parentId(comment.getParent().getId())
                .commentText(comment.getCommentText())
                .time(Timestamp.valueOf(comment.getTime()))
                .build();
    }

    public static Comment convertCommentDTOToComment(CommentDTO commentDto)    {
        return Comment.builder()
                .parent(commentService.getCommentById(commentDto.getParentId()))
                .commentText(commentDto.getCommentText())
                .time(commentDto.getTime().toLocalDateTime())
                .build();
    }

    public static Set<CommentDTO> convertSetCommentToSetCommentDTO(Set<Comment> comments)  {
        return comments.stream()
                .map(CommentMapper::convertCommentToCommentDTO)
                .collect(Collectors.toSet());
    }

    public static Set<Comment> convertSetCommentDTOToSetComment(Set<CommentDTO> comments)  {
        return comments.stream()
                .map(CommentMapper::convertCommentDTOToComment)
                .collect(Collectors.toSet());
    }

}
