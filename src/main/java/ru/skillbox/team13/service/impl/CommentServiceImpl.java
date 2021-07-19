package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.dto.DTOWrapper;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

        List<CommentDto> combinedComments = combineComments(page.getContent());
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

        return WrapperMapper.wrap(getCommentDto(commentId), true);
    }

    private CommentDto getCommentDto(int commentId) {
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        List<CommentDto> raw = commentDAO.getCommentDTO(currentPersonId, commentId);
        return combineComments(raw).get(0);
    }

    private List<CommentDto> combineComments(List<CommentDto> raw) {  //todo merge with similar in post service
        Map<Boolean, List<CommentDto>> filtered = raw.stream()
                .collect(Collectors.partitioningBy(commentDto -> Objects.isNull(commentDto.getParentId())));

        List<CommentDto> topLevel = filtered.get(true);
        List<CommentDto> others = filtered.get(false);

        Map<Integer, List<CommentDto>> lowerLevelComments = others.stream().collect(Collectors.groupingBy(CommentDto::getParentId));

        for (CommentDto comment : raw) {
            if (lowerLevelComments.containsKey(comment.getId())) {
                comment.getComments().addAll(lowerLevelComments.get(comment.getId()));
            }
        }

        if (topLevel.size() == 0) {
            return others;
        } else return topLevel;  //todo ugly :(
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

        return WrapperMapper.wrap(getCommentDto(commentId), true);
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

        return WrapperMapper.wrap(Collections.singletonList(getCommentDto(commentId)), true);
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
