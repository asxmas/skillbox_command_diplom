package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.AddCommentDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.CommentService;

import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class CommentsController {

    private final CommentService commentService;

    @GetMapping("{id}/comments")
    //Получение комментариев на публикации
    public DTOWrapper getComments(@PathVariable int id,
                                  @RequestParam(required = false, defaultValue = "0") int offset,
                                  @RequestParam(required = false, defaultValue = "20") int itemPerPage) {
        return commentService.getComments(id, offset, itemPerPage);
    }

    @PostMapping("{id}/comments")
    //Создание комментария к публикации
    public DTOWrapper postComment(@PathVariable int id, @RequestBody AddCommentDto dto) {
        return (isNull(dto.getParentId())) ?
                commentService.postTopLevelComment(id, dto.getCommentText()) :
                commentService.postCommentToComment(id, dto.getParentId(), dto.getCommentText());
    }

    @PutMapping("{id}/comments/{comment_id}")
    //Редактирование комментария к публикации
    public DTOWrapper editComment(@PathVariable int id, @PathVariable("comment_id") int commentId) {
        return null;//todo implement
    }

    @DeleteMapping("{id}/comments/{comment_id}")
    //Удаление комментария к публикации
    public DTOWrapper deleteComment(@PathVariable int id, @PathVariable("comment_id") int commentId) {
        return null;//todo implement
    }

    @PutMapping("{id}/comments/{comment_id}/recover")
    //Восстановление комментария
    public DTOWrapper restoreComment(@PathVariable int id, @PathVariable("comment_id") int commentId) {
        return null;//todo implement
    }

}