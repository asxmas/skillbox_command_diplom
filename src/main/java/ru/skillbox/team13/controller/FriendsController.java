package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.UserIdsDto;
import ru.skillbox.team13.service.impl.FriendsServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class FriendsController {

    private final FriendsServiceImpl friendService;

    @GetMapping("friends")
    //Получить список друзей
    public ResponseEntity<DTOWrapper> getFriends(@RequestParam(required = false, defaultValue = "") String name,
                                                       @RequestParam(required = false, defaultValue = "0") int offset,
                                                       @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<>(friendService.getFriends(name, offset, itemPerPage), HttpStatus.OK);
    }

    @DeleteMapping("friends/{id}")
    //Удалить пользователя из друзей
    public ResponseEntity<Object> deleteFriend(@PathVariable Integer id) {
        return new ResponseEntity<>(friendService.deleteFriend(id), HttpStatus.OK);
    }

    @PostMapping("friends/{id}")
    //Принять/Добавить пользователя в друзья
    public ResponseEntity<Object> acceptAddFriend(@PathVariable Integer id) {
        return new ResponseEntity<>(friendService.acceptRequestAndAddFriend(id), HttpStatus.OK);
    }

    @GetMapping("/friends/request")
    //Получить список входящик заявок на добавление в друзья
    public ResponseEntity<DTOWrapper> getFriendshipRequests(@RequestParam(required = false, defaultValue = "") String name,
                                                            @RequestParam(required = false, defaultValue = "0") int offset,
                                                            @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<>(friendService.getFriendshipRequests(name, offset, itemPerPage), HttpStatus.OK);
    }

    @GetMapping("/friends/recommendations")
    //Получить список рекомендаций
    public ResponseEntity<DTOWrapper> getRecommendations(@RequestParam(required = false, defaultValue = "0") int offset,
                                                         @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<>(friendService.getRecommendations(offset, itemPerPage), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/is/friends")
    //Получить информацию является ли пользователь другом указанных пользователей
    public ResponseEntity<DTOWrapper> isFriends(@RequestBody UserIdsDto userIds) {
        return new ResponseEntity<>(friendService.getStatusForIds(userIds.getUserIds()), HttpStatus.OK);
    }
}
