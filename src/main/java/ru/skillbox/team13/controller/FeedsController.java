package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.FeedsService;

@RestController
@RequiredArgsConstructor
public class FeedsController {

    private final FeedsService feedsService;

    //Получение списка новостей
    ResponseEntity<DTOWrapper> getFeed(@RequestParam(defaultValue = "") String name,
                                       @RequestParam(required = false, defaultValue = "0") int offset,
                                       @RequestParam(required = false, defaultValue = "20") int itemPerPage){
        return new ResponseEntity<>(feedsService.serve(name, offset, itemPerPage), HttpStatus.OK);
    }
}
