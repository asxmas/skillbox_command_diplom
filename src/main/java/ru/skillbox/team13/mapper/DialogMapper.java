package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.DialogDto;
import ru.skillbox.team13.dto.DialogMessageDto;
import ru.skillbox.team13.entity.Dialog;
import ru.skillbox.team13.entity.Message;

import java.time.OffsetDateTime;

public class DialogMapper {

    public static DialogMessageDto convertMessageToDialogMessageDTO(Message message) {
        return DialogMessageDto.builder()
                .id(message.getId())
                .time(message.getTime().toEpochSecond(OffsetDateTime.now().getOffset()) * 1000)
                .authorId(message.getAuthor().getId())
                .recipientId(message.getRecipient().getId())
                .messageText(message.getMessageText())
                .readStatus(message.getReadStatus().name())
                .build();
    }

    public static DialogDto convertDialogToDialogDTO(Dialog dialog) {
        return DialogDto.builder()
                .id(dialog.getId())
                .unreadCount(dialog.getUnreadCount())
                .lastMessage(convertMessageToDialogMessageDTO(dialog.getLastMessage()))
                .build();
    }
}
