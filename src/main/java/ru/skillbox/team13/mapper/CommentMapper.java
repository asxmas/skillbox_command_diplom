package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.service.CommentService;

import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
@RequiredArgsConstructor
public class CommentMapper {

    private final CommentService commentService;

//    public static CommentDto convertCommentToCommentDTO(Comment comment)    {
//        return CommentDto.builder()
//                .parentId(comment.getParent().getId())
//                .text(comment.getCommentText())
//                .time(comment.getTime().getSecond()*1000)
//                .build();
//    }

//    public static Comment convertCommentDTOToComment(CommentDto commentDto)    {
//        Comment comment = new Comment();
//        Comment parent = commentService.getCommentById(commentDto.getParentId());
//        String commentText = commentDto.getCommentText();
//        LocalDateTime time = commentDto.getTime().toLocalDateTime();
//        comment.setParent(parent);
//        comment.setCommentText(commentText);
//        return comment;
//    }
//
//    public static Set<CommentDto> convertSetCommentToSetCommentDTO(Set<Comment> comments)  {
//        return comments.stream()
//                .map(CommentMapper::convertCommentToCommentDTO)
//                .collect(Collectors.toSet());
//    }

}
