package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.AddPostDto;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.PostService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{id}/wall")
public class WallController {

    private final PostService postService;

    @GetMapping
    //Получение записей на стене пользователя
    public ResponseEntity<DTOWrapper> getListPosts(@PathVariable("id") int id,
                                                   @RequestParam(required = false, defaultValue = "0") int offset,
                                                   @RequestParam(required = false, defaultValue = "20") int itemPerPage) {
        return ResponseEntity.ok(postService.getWallForUserId(id, offset, itemPerPage));
    }

    @PostMapping
    //Добавление публикации на стену пользователя
    public ResponseEntity<DTOWrapper> createPost(@PathVariable("id") Integer id,
                                                 @RequestParam(value = "publish_date", required = false) Long pubDate,
                                                 @RequestBody AddPostDto payload) {
        String title = payload.getTitle();
        String text = payload.getPostText();
        Set<String> tags = Arrays.stream(payload.getTags()).collect(Collectors.toSet());
        return ResponseEntity.ok(postService.post(title, text, tags, id, pubDate));
    }
}
