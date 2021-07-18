package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.AddCommentDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class CommentsController {

    private final CommentService commentService;

    @GetMapping("{id}/comments")
    //Получение комментариев на публикации
    public ResponseEntity<DTOWrapper> getComments(@PathVariable int id,
                                                  @RequestParam(required = false, defaultValue = "0") int offset,
                                                  @RequestParam(required = false, defaultValue = "20") int itemPerPage) {
        return ResponseEntity.ok(commentService.getComments(id, offset, itemPerPage));
    }

    @PostMapping("{id}/comments")
    //Создание комментария к публикации
    public ResponseEntity<DTOWrapper> postComment(@PathVariable int id, @RequestBody AddCommentDto dto) {
        return ResponseEntity.ok(commentService.postComment(id, dto.getParentId(), dto.getCommentText()));
    }

    @PutMapping("{id}/comments/{comment_id}")
    //Редактирование комментария к публикации
    public ResponseEntity<DTOWrapper> editComment(@PathVariable int id, @PathVariable("comment_id") int commentId,
                                                  @RequestBody AddCommentDto dto) {
        return ResponseEntity.ok(commentService.editComment(commentId, dto.getCommentText()));
    }

    @DeleteMapping("{id}/comments/{comment_id}")
    //Удаление комментария к публикации
    public ResponseEntity<DTOWrapper> deleteComment(@PathVariable int id, @PathVariable("comment_id") int commentId) {
        return ResponseEntity.ok(commentService.delete(commentId));
    }

    @PutMapping("{id}/comments/{comment_id}/recover")
    //Восстановление комментария
    public ResponseEntity<DTOWrapper> restoreComment(@PathVariable int id, @PathVariable("comment_id") int commentId) {
        return ResponseEntity.ok(commentService.restore(commentId));
    }
}