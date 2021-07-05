package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.CommentToPostDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
public class CommentMapper {



    public static Comment mapToCommentEntity(CommentToPostDto commentToPostDto,
                                             Post post,
                                             CommentRepository commRep,
                                             UserService userService) {
        Comment comment = new Comment();
        comment.setTime(LocalDateTime.now());
        comment.setPost(post);
        comment.setParent(takeComment(commentToPostDto, commRep));
        comment.setAuthor(userService.getAuthorizedUser().getPerson());
        comment.setCommentText(commentToPostDto.getText());
        comment.setBlocked(true);
        comment.setChildComments(new HashSet<>());
        comment.setLikes(new HashSet<>());
        return comment;
    }

    private static Comment takeComment(CommentToPostDto commentToPostDto, CommentRepository commentRep) {
        if(commentToPostDto.getParentId() != null) {
            Optional<Comment> comment = commentRep.findById(commentToPostDto.getParentId());
            return comment.isPresent() ? comment.get() : null;
        }
        return null;
    }
}
