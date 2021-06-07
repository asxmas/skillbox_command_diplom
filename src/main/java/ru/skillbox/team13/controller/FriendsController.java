package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.service.FriendsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class FriendsController {

    private final FriendsService friendService;

    @GetMapping("friends")
    public ResponseEntity<DTOWrapper> getFriends(@RequestParam String name,
                                                   @RequestParam(required = false, defaultValue = "0") int offset,
                                                   @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        int count = friendService.countByName(name);
        List<PersonDTO> results = friendService.findByName(name, offset, itemPerPage);
        return new ResponseEntity<>(WrapperMapper.wrap(results, count, offset, itemPerPage), HttpStatus.OK);
    }
//
//    @DeleteMapping("friends/{id}")
//    public ResponseEntity<Object> deleteFriend() {
//
//    }
//
//    @PostMapping("friends/{id}")
//    public ResponseEntity<Object> acceptFriend() {
//
//    }
//
//    @GetMapping("/friends/request")
//    public ResponseEntity<List<Friendship>> getFriendsipRequests() {
//
//    }
//
//    @GetMapping("/friends/recommendations")
//    public ResponseEntity<Person> getRecommendations() {
//
//    }
//
//    @PostMapping("/is/friends")
//    public ResponseEntity<Object> isFriends() {
//
//    }
}
