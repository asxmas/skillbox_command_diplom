package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.DialogsService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class DialogsController {

    private final DialogsService dialogsService;

    @GetMapping("dialogs")
    //получение списка диалогов
    public ResponseEntity<DTOWrapper> getDialogs(@RequestParam(required = false, defaultValue = "") String query,
                                                 @RequestParam(required = false, defaultValue = "0") int offset,
                                                 @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return ResponseEntity.ok(dialogsService.getDialogs(query, offset, itemPerPage));
    }

    @PostMapping("dialogs")
    //создание диалога между переданными в параметре Ids участников
    public ResponseEntity<DTOWrapper> createDialog(@PathVariable ArrayList<Integer> userIds) {
        return ResponseEntity.ok(dialogsService.createDialog(userIds));
    }

    @GetMapping("dialogs/unreaded")
    //получение общего количества непрочитанных сообщений
    public ResponseEntity<DTOWrapper> getUnreadCount() {
        return ResponseEntity.ok(dialogsService.getUnreadCount());
    }

    @DeleteMapping("dialogs/{id}")
    //удалить диалог
    public ResponseEntity<Object> deleteDialog(@PathVariable Integer id) {
        return ResponseEntity.ok(dialogsService.deleteDialog(id));
    }
}
