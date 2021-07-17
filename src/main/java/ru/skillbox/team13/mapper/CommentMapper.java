package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.service.CommentService;

import java.util.Set;
import ru.skillbox.team13.dto.CommentToPostDto;
import ru.skillbox.team13.dto.PostCommentDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommentMapper {

    private final CommentService commentService;

    public static CommentDto convertCommentToCommentDTO(Comment comment)    {
        return CommentDto.builder()
                .parentId(comment.getParent().getId())
                .text(comment.getCommentText())
                .time(comment.getTime().getSecond()*1000)
                .build();
    }

//    public static Comment convertCommentDTOToComment(CommentDto commentDto)    {
//        Comment comment = new Comment();
//        Comment parent = commentService.getCommentById(commentDto.getParentId());
//        String commentText = commentDto.getCommentText();
//        LocalDateTime time = commentDto.getTime().toLocalDateTime();
//        comment.setParent(parent);
//        comment.setCommentText(commentText);
//        return comment;
//    }

    public static Set<CommentDto> convertSetCommentToSetCommentDTO(Set<Comment> comments)  {
        return comments.stream()
                .map(CommentMapper::convertCommentToCommentDTO)
                .collect(Collectors.toSet());
    }




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

    public static List<CommentDto> getCommentsForPostId(int pId, List<CommentProjection> comments) {
        return comments.stream().filter(c -> c.getPostId() == pId)
                .map(f -> CommentDto.builder()
                        .parentId(f.getParentId())
                        .text(f.getText())
                        .id(f.getId())
                        .postId((f.getPostId())) //todo !! api wants string
                        .time(TimeUtil.getTimestamp(f.getTime()))
                        .authorId(f.getAuthorId())
                        .isBlocked(f.getBlocked())
                        .build())
                .collect(Collectors.toList());
    }

//    public static PostCommentDto mapToPostCommentDto(CommentToPostDto commentToPost) {
//        PostCommentDto postComment = PostCommentDto.builder()
//                .parentId(commentToPost.getParentId())
//                .commentText(commentToPost.getText())
//                .id(commentToPost.get);
//    }
}
