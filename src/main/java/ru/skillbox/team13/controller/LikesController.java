package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.LikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class LikesController {

    private final LikeService likeService;

    @GetMapping("liked")
    //Был ли поставлен лайк пользователем
    public ResponseEntity<DTOWrapper> isLikedBy(@RequestParam(required = false, name = "user_id") Integer userId,
                                                @RequestParam(name = "item_id") int itemId,
                                                @RequestParam String type) {
        return new ResponseEntity<>(likeService.isLikedBy(userId, itemId, type), HttpStatus.OK);
    }

    @GetMapping("likes")
    //Получить список пользователей оставивших лайк
    public ResponseEntity<DTOWrapper> getLikers(@RequestParam(name = "item_id") int itemId,
                                                @RequestParam String type) {
        return new ResponseEntity<>(likeService.getLikedBy(itemId, type), HttpStatus.OK);
    }
}
