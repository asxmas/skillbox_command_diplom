package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationService noteService;

    @GetMapping()
    public ResponseEntity<DTOWrapper> getAllNotifications(@RequestParam(required = false, defaultValue = "0") int offset,
                                                           @RequestParam(required = false, defaultValue = "10") int limit) {
        return new ResponseEntity<DTOWrapper>(noteService.getAllNotification(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<DTOWrapper> readNotifications(@RequestParam(required = false, defaultValue = "- 1") int id,
                                                        @RequestParam(required = false, defaultValue = "false") boolean isReadAll) {
        return new ResponseEntity<DTOWrapper>(noteService.getNotificationById(id, isReadAll), HttpStatus.OK);

    }
}
