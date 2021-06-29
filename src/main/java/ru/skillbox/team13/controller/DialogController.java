package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.PlainObjectDto;
import ru.skillbox.team13.service.DialogService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class DialogController {

    private final DialogService dialogService;

    @GetMapping("dialogs")
    //получение списка диалогов
    public ResponseEntity<DTOWrapper> getDialogs(@RequestParam(required = false, defaultValue = "") String query,
                                                 @RequestParam(required = false, defaultValue = "0") int offset,
                                                 @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return ResponseEntity.ok(dialogService.getPersonDialogs(query, offset, itemPerPage));
    }

    @PostMapping("dialogs")
    //создание диалога
    public ResponseEntity<DTOWrapper> createDialog(@RequestBody PlainObjectDto userIdsArray) {
        ArrayList<Integer> userIds = (ArrayList<Integer>) userIdsArray.getProperties().get("user_ids");
        return ResponseEntity.ok(dialogService.createDialog(userIds));
    }

    @GetMapping("dialogs/unreaded")
    //получение общего количества непрочитанных сообщений
    public ResponseEntity<DTOWrapper> getUnreadCount() {
        return ResponseEntity.ok(dialogService.getUnreadCount());
    }

    @DeleteMapping("dialogs/{id}")
    //удалить диалог
    public ResponseEntity<DTOWrapper> deleteDialog(@PathVariable Integer id) {
        return ResponseEntity.ok(dialogService.deleteDialog(id));
    }

    @PostMapping("dialogs/{id}/messages")
    //отправка сообщения
    public ResponseEntity<DTOWrapper> sendMessage(@PathVariable("id") Integer dialogId,
                                                  @RequestBody PlainObjectDto messageTextObject) {
        String messageText = (String) messageTextObject.getProperties().get("message_text");
        return ResponseEntity.ok(dialogService.sendMessage(dialogId, messageText));
    }
}
