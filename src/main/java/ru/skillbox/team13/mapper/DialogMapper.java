package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.DialogDto;
import ru.skillbox.team13.dto.DialogMessageDto;
import ru.skillbox.team13.entity.Dialog2Person;
import ru.skillbox.team13.entity.Message;

import java.time.OffsetDateTime;

public class DialogMapper {

    public static DialogMessageDto convertMessageToDialogMessageDTO(Message message) {
        return DialogMessageDto.builder()
                .id(message.getId())
                .time(message.getTime().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli())
                .authorId(message.getAuthor().getId())
                .recipientId(message.getRecipient().getId())
                .messageText(message.getMessageText())
                .readStatus(message.getReadStatus().name())
                .build();
    }

    public static DialogDto convertDialog2PersonToDialogDTO(Dialog2Person dialog2Person) {
        return DialogDto.builder()
                .id(dialog2Person.getDialog().getId())
                .unreadCount(dialog2Person.getUnreadCount())
                .lastMessage(convertMessageToDialogMessageDTO(dialog2Person.getDialog().getLastMessage()))
                .build();
    }
}
