package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.enums.MessageReadStatus;

import java.util.ArrayList;

public interface DialogService {

    DTOWrapper getPersonDialogs(String query, int offset, int itemPerPage);

    DTOWrapper createDialog(ArrayList<Integer> userIds);

    DTOWrapper getUnreadCount();

    DTOWrapper deleteDialog(int dialogId);

    DTOWrapper sendMessage(int dialogId, String messageText);

    DTOWrapper getDialogMessages(int dialogId, String query, int offset, int itemPerPage);

    DTOWrapper setStatus(int messageId, MessageReadStatus read);
}
