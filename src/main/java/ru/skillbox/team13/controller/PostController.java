package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.mapper.PostMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.RepoPost;
import ru.skillbox.team13.service.PostsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")

public class PostController {

    private final PostsService postsService;

    @GetMapping("post/{id}")
    @ResponseBody
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Integer id) {
        PostDTO postDTO = PostMapper.convertPostToPostDTO(postsService.getPostById(id));
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }



}
