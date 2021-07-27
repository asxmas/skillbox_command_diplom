package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.PostService;

@RestController
@RequiredArgsConstructor
public class FeedsController {

    private final PostService postService;

    //Получение списка новостей
    @GetMapping("/api/v1/feeds")
    ResponseEntity<DTOWrapper> getFeed(@RequestParam(defaultValue = "") String name, //frontend does not use 'name'
                                       @RequestParam(required = false, defaultValue = "0") int offset,
                                       @RequestParam(required = false, defaultValue = "20") int itemPerPage){
        return new ResponseEntity<>(postService.getFeed(name, offset, itemPerPage), HttpStatus.OK);
    }
}
