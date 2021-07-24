package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.DialogDto;
import ru.skillbox.team13.dto.DialogMessageDto;
import ru.skillbox.team13.entity.Dialog2Person;
import ru.skillbox.team13.entity.Message;
import ru.skillbox.team13.entity.Person;

import java.time.OffsetDateTime;

public class DialogMapper {

    public static DialogMessageDto convertMessageToDialogMessageDTO(Message message) {
        return DialogMessageDto.builder()
                .id(message.getId())
                .time(message.getTime().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli())
                .authorId(message.getAuthor().getId())
                .recipient(PersonMapper.convertPersonToPersonDTO(message.getRecipient()))
                .messageText(message.getMessageText())
                .readStatus(message.getReadStatus().name())
                .build();
    }

    public static DialogMessageDto convertMessageToDialogMessageDTO(Message message, Boolean isSentByMe) {
        return DialogMessageDto.builder()
                .id(message.getId())
                .time(message.getTime().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli())
                .messageText(message.getMessageText())
                .readStatus(message.getReadStatus().name())
                .isSentByMe(isSentByMe)
                .build();
    }

    public static DialogDto convertDialog2PersonToDialogDTO(Dialog2Person dialog2Person, Person recipient) {

        DialogMessageDto dialogMessageDto;
        if (dialog2Person.getDialog().getLastMessage() != null) {
            dialogMessageDto = convertMessageToDialogMessageDTO(dialog2Person.getDialog().getLastMessage(),
                    dialog2Person.getDialog().getLastMessage().getAuthor().getId() == dialog2Person.getPerson().getId());
        }
        else {
            dialogMessageDto = DialogMessageDto.builder().build();
        }
        dialogMessageDto.setRecipient(PersonMapper.convertPersonToPersonDTO(recipient));
        return DialogDto.builder()
                .id(dialog2Person.getDialog().getId())
                .unreadCount(dialog2Person.getUnreadCount())
                .lastMessage(dialogMessageDto)
                .build();
    }
}
