package ru.skillbox.team13.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.impl.FriendsServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class FriendsController {

    private final FriendsServiceImpl friendService;

    @GetMapping("friends")
    public ResponseEntity<DTOWrapper> findFriendByName(@RequestParam String name,
                                                       @RequestParam(required = false, defaultValue = "0") int offset,
                                                       @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<>(friendService.findFriendByName(name, offset, itemPerPage), HttpStatus.OK);
    }

    @DeleteMapping("friends/{id}")
    public ResponseEntity<Object> deleteFriend(@PathVariable Integer id) {
        return new ResponseEntity<>(friendService.deleteFriend(id), HttpStatus.OK);
    }

    @PostMapping("friends/{id}")
    public ResponseEntity<Object> acceptFriend(@PathVariable Integer id) {
        return new ResponseEntity<>(friendService.addOrAcceptFriend(id), HttpStatus.OK);
    }

    @GetMapping("/friends/request")
    public ResponseEntity<DTOWrapper> getFriendshipRequests(@RequestParam String name,
                                                            @RequestParam(required = false, defaultValue = "0") int offset,
                                                            @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<>(friendService.getFriendshipRequests(name, offset, itemPerPage), HttpStatus.OK);
    }

    @GetMapping("/friends/recommendations")
    public ResponseEntity<DTOWrapper> getRecommendations(@RequestParam(required = false, defaultValue = "0") int offset,
                                                         @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return new ResponseEntity<>(friendService.getRecommendations(offset, itemPerPage), HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/is/friends")
    public ResponseEntity<DTOWrapper> isFriends(@RequestBody String data) {
        JsonNode content = new ObjectMapper().readTree(data).get("user_ids");
        List<Integer> ids = new ArrayList<>();
        content.forEach(jsonNode -> ids.add(jsonNode.asInt()));
        return new ResponseEntity<>(friendService.getStatusForIds(ids), HttpStatus.OK);
    }
}
