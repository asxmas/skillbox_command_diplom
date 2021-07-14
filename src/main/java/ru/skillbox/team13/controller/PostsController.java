package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.CommentToPostDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.repository.CommentRepository;
import ru.skillbox.team13.service.CommentService;
import ru.skillbox.team13.service.PostService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostsController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping()
//Поиск публикации
    ResponseEntity<DTOWrapper> findPost(@RequestParam String text,
                                        @RequestParam(required = false, name = "date_from") Long timestampFrom,
                                        @RequestParam(required = false, name = "date_to") Long timestampTo,
                                        @RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<>(postService.find(text, timestampFrom, timestampTo, offset, itemPerPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
//Получение публикации по ID
    ResponseEntity<DTOWrapper> getPost(@PathVariable int id) {
        return new ResponseEntity<>(postService.getById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
//Редактирование публикации по ID
    ResponseEntity<DTOWrapper> editPost(@PathVariable int id,
                                        @RequestParam(name = "publish_date", required = false) Long pubDate, //todo wtf??
                                        @RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(postService.edit(id, pubDate, payload.get("title"), payload.get("post_text")), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
//Удаление публикации по ID
    ResponseEntity<DTOWrapper> deletePost(@PathVariable int id) {
        return new ResponseEntity<>(postService.deleteById(id), HttpStatus.OK);
    }

    @PutMapping("{id}/recover")
//Восстановление публикации по ID
    ResponseEntity<DTOWrapper> recoverPost(@PathVariable int id) {
        return new ResponseEntity<>(postService.recoverById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/comments")
        //Получение комментариев к посту
    ResponseEntity<DTOWrapper> takeCommentByPostId(@PathVariable int id,
                                                   @RequestParam(required = false, defaultValue = "0") int offset,
                                                   @RequestParam(required = false, defaultValue = "20") int itemPerPage) {
        return new ResponseEntity<>(commentService.getAllCommentByPost(id, offset, itemPerPage), HttpStatus.OK);
    }

    @PostMapping("/{id}/comments")
    ResponseEntity<DTOWrapper> insertCommentToPost(@PathVariable int id,
                                                   @RequestBody(required = true) CommentToPostDto commentToPostDto) {

    }

//    @PutMapping("/{id}/comments/{comment_id}")
//    ResponseEntity<DTOWrapper> editCommentToPost() {
//    }
//
//    @DeleteMapping("/{id}/comments/{comment_id}")
//    ResponseEntity<DTOWrapper> deleteComment() {
//
//    }
//
//    @PutMapping("/{id}/comments/{comment_id}/recover")
//    ResponseEntity<DTOWrapper> restoreComment() {
//
//    }
//
//    @PostMapping("{id}/report")
//    ResponseEntity<DTOWrapper> reportToPost() {
//
//    }
//
//    @PostMapping("{id}/comments/{comment_id}/report")
//    ResponseEntity<DTOWrapper> reportToComment() {
//
//    }




}
