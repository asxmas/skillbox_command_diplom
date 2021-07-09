package ru.skillbox.team13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.LongpollParamDto;
import ru.skillbox.team13.dto.PlainObjectDto;
import ru.skillbox.team13.entity.enums.MessageReadStatus;
import ru.skillbox.team13.service.DialogService;
import ru.skillbox.team13.service.LongpollService;
import ru.skillbox.team13.service.UserService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class DialogController {

    private final DialogService dialogService;
    private final LongpollService longpollService;
    private final UserService userService;

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

    @GetMapping("dialogs/{id}/messages")
    public ResponseEntity<DTOWrapper> getDialogMessages(@PathVariable("id") Integer dialogId,
                                                        @RequestParam(required = false, defaultValue = "") String query,
                                                        @RequestParam(required = false, defaultValue = "0") int offset,
                                                        @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        return ResponseEntity.ok(dialogService.getDialogMessages(dialogId, query, offset, itemPerPage));
    }

    @PutMapping("dialogs/{dlg_id}/messages/{msg_id}/read")
    //Пометить сообщение как "Прочитанное"
    public ResponseEntity<DTOWrapper> markMessageAsRead(@PathVariable("dlg_id") int dialogId, //dialog id is not used
                                                        @PathVariable("msg_id") int messageId) {
        return ResponseEntity.ok(dialogService.setStatus(messageId, MessageReadStatus.READ));
    }

    @GetMapping("dialogs/{dlg_id}/activity/{user_id}")
    //Получить последнюю активность и текущий статус для пользователя с которым ведется диалог
    public ResponseEntity<DTOWrapper> getUserStatus(@PathVariable("dlg_id") int dialogId, //dialog id is not used
                                                    @PathVariable("user_id") int userId) {
        return ResponseEntity.ok(userService.getUserActivity(userId));
    }

    @PostMapping("dialogs/{dlg_id}/activity/{user_id}")
    //    Изменить статус набора текста пользователем в диалоге.
    public ResponseEntity<DTOWrapper> setUserDialogStatus(@PathVariable("dlg_id") int dialogId,
                                                          @PathVariable("user_id") int userId) {
        return ResponseEntity.ok(dialogService.setUserDialogStatus(dialogId, userId));
    }

    @GetMapping("dialogs/longpoll")
    //Получить данные для подключения к longpoll серверу
    public ResponseEntity<DTOWrapper> getLongpollConnectionData() {
        return ResponseEntity.ok(longpollService.getConnectionData());
    }

    @PostMapping("dialogs/longpoll/history")
    //Получить обновления личных сообщений пользователя
    public ResponseEntity<DTOWrapper> getUpdates(@RequestBody LongpollParamDto paramDto) {
        return ResponseEntity.ok(longpollService.getSomething(paramDto));
    }
}
