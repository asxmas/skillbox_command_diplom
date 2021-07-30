package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.AddPostDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.SearchPostDto;
import ru.skillbox.team13.service.PostService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostsController {

    private final PostService postService;

    @GetMapping()
//Поиск публикации
    ResponseEntity<DTOWrapper> findPost(@RequestBody SearchPostDto searchDto,
                                        @RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        String text = searchDto.getText();
        Long timestampFrom = searchDto.getTimestampFrom();
        Long timestampTo = searchDto.getTimestampTo();
        String authorName = searchDto.getAuthor();
        String[] tags = searchDto.getTags();

        return new ResponseEntity<>(postService.find(text, timestampFrom, timestampTo,
                authorName, tags, offset, itemPerPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
//Получение публикации по ID
    ResponseEntity<DTOWrapper> getPost(@PathVariable int id) {
        return new ResponseEntity<>(postService.getById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
//Редактирование публикации по ID
    ResponseEntity<DTOWrapper> editPost(@PathVariable int id,
                                        @RequestParam(name = "publish_date", required = false) Long pubDate,
                                        @RequestBody AddPostDto payload) {
        String title = payload.getTitle();
        String text = payload.getPostText();
        Set<String> tags = Arrays.stream(payload.getTags()).collect(Collectors.toSet());
        return new ResponseEntity<>(postService.edit(id, pubDate, title, tags, text), HttpStatus.OK);
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
}
