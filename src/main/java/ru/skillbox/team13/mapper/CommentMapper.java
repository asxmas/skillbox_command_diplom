package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.CommentDTO;
import ru.skillbox.team13.entity.Comment;

import java.sql.Timestamp;

@RequiredArgsConstructor
public class CommentMapper {
    public static CommentDTO convertCommentToCommentDTO(Comment comment)    {
        CommentDTO commentDTO = CommentDTO.builder()
                .parentId(comment.getParent().getId())
                .commentText(comment.getCommentText())
                .time(Timestamp.valueOf(comment.getTime()))
                .build();
        return commentDTO;
    }

}
