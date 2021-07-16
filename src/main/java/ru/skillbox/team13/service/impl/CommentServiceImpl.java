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
        Page<CommentDto> page = commentDAO.getCommentDtosForPostIds(postId, getPageable(offset, itemPerPage));
        log.debug("Loading {} page of comments for post id={}, total {}.",
                offset / itemPerPage, postId, page.getTotalElements());
        return WrapperMapper.wrap(page.getContent(), (int) (page.getTotalElements()), offset, itemPerPage, true);
    }

    @Override
    @Modifying
    @Transactional
    public DTOWrapper postTopLevelComment(int id, String text) {
        Person author = userService.getAuthorizedUser().getPerson();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No post for id=" + id + " id found"));
        Comment comment = new Comment(LocalDateTime.now(), post, author, text, false);
        log.debug("Posting comment '{}' on post id={}", text, id);
        int commentId = commentRepository.save(comment).getId();
        CommentDto commentDto = commentDAO.getCommentDtoForId(commentId);
        return WrapperMapper.wrap(commentDto, true);
    }

    @Override
    public DTOWrapper postCommentToComment(int id, Integer parentId, String text) {
        return null;  //todo implement
    }
}
