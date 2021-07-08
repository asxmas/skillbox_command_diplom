package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.CommentDTO;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.service.CommentService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        Comment comment = new Comment();
        Comment parent = commentService.getCommentById(commentDto.getParentId());
        String commentText = commentDto.getCommentText();
        LocalDateTime time = commentDto.getTime().toLocalDateTime();
        comment.setParent(parent);
        comment.setCommentText(commentText);
        return comment;
    }

    public static Set<CommentDTO> convertSetCommentToSetCommentDTO(Set<Comment> comments)  {
        return comments.stream()
                .map(CommentMapper::convertCommentToCommentDTO)
                .collect(Collectors.toSet());
    }

}
