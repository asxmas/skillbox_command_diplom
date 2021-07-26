package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.repository.PostRepository;
import ru.skillbox.team13.repository.QueryDSL.CommentDAO;
import ru.skillbox.team13.service.CommentService;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.CommentUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;
import static ru.skillbox.team13.util.PageUtil.getPageable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;
    private final UserService userService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;  //todo move to CommentDAO


    @Override
    public DTOWrapper getComments(int postId, int offset, int itemPerPage) {
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        Page<CommentDto> page = commentDAO.getCommentDTOs(currentPersonId, postId, getPageable(offset, itemPerPage));
        log.debug("Loading {} page of comments for post id={}, total {}.",
                offset / itemPerPage, postId, page.getTotalElements());

        List<CommentDto> combinedComments = CommentUtil.twoLevelCommentSort(page.getContent());
        return WrapperMapper.wrap(combinedComments, (int) (page.getTotalElements()), offset, itemPerPage, true);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper postComment(int postId, Integer parentCommentId, String text) {
        Comment comment = createComment(postId, text);
        if (nonNull(parentCommentId)) {
            comment.setParent(getComment(parentCommentId));
            log.debug("Posting comment '{}' on post id={}, parent comment id={}", text, postId, parentCommentId);
        } else {
            log.debug("Posting comment '{}' on post id={}", text, postId);
        }
        int commentId = commentRepository.save(comment).getId();

//      return WrapperMapper.wrap(getCommentDto(commentId), true);  //unnecessary DB load
        return WrapperMapper.wrapMessage(new MessageDTO("OK id="+commentId + " text=" + text));
    }

    private CommentDto getCommentDto(int commentId) {
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        List<CommentDto> raw = commentDAO.getCommentDTO(currentPersonId, commentId);
        return CommentUtil.twoLevelCommentSort(raw).get(0);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper editComment(int commentId, String commentText) {
        Comment comment = getComment(commentId);
        comment.setCommentText(commentText);
        comment.setTime(LocalDateTime.now());
        log.debug("Editing comment id={}: '{}' -> '{}'.", commentId, comment.getCommentText(), commentText);

        commentRepository.save(comment);

//      return WrapperMapper.wrap(getCommentDto(commentId), true); //unnecessary DB load
        return WrapperMapper.wrapMessage(new MessageDTO("OK id="+commentId + " text=" + commentText));
    }

    @Override
    public DTOWrapper delete(int commentId) {
        Comment comment = getComment(commentId);
        comment.setDeleted(true);
        log.debug("Deleting comment id={}.", commentId);
        commentRepository.save(comment);
        return WrapperMapper.wrap(Map.of("id", commentId), true);
    }

    @Override
    public DTOWrapper restore(int commentId) {
        Comment comment = getComment(commentId);
        comment.setDeleted(false);
        log.debug("Restoring comment id={}.", commentId);
        commentRepository.save(comment);

//      return WrapperMapper.wrap(Collections.singletonList(getCommentDto(commentId)), true); unnecessary DB load
        return WrapperMapper.wrapMessage(new MessageDTO("OK id="+commentId + " text=" + comment.getCommentText()));
    }

    @Override
    public DTOWrapper deleteCommentsForAuthor(int authorId) {
        Person currentPerson = userService.getAuthorizedUser().getPerson();
        Set<Comment> comments = commentRepository.findAllByAuthor(currentPerson);
        log.debug("Deleting {} comments.", comments.size());
        comments.forEach(c -> c.setDeleted(true));
        return WrapperMapper.wrapMessage("OK");
    }

    private Comment createComment(int postId, String text) {
        Person author = userService.getAuthorizedUser().getPerson();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException("No post for id=" + postId + " id found"));
        return new Comment(LocalDateTime.now(), post, author, text, false);
    }

    private Comment getComment(int id) {
        return commentRepository.findById(id).orElseThrow(() -> new BadRequestException("No comment found for id=" + id));
    }
}
