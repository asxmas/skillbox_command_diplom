package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{id}/wall")
public class WallController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<DTOWrapper> getListPosts(@PathVariable("id") int id,
                                                   @RequestParam(required = false, defaultValue = "0") int offset,
                                                   @RequestParam(required = false, defaultValue = "20") int itemPerPage) {
        return ResponseEntity.ok(postService.getWallForUserId(id, offset, itemPerPage));
    }

    @PostMapping
    public ResponseEntity<DTOWrapper> createPost(@PathVariable("id") Integer id,
                                                 @RequestParam(value = "publish_date", required = false) Long pubDate,
                                                 @RequestBody PostDto postDTO) {
        return ResponseEntity.ok(postService.post(postDTO, id, pubDate));
    }
}
