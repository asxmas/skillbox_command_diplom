package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

import java.util.ArrayList;

public interface DialogService {

    DTOWrapper getPersonDialogs(String query, int offset, int itemPerPage);

    DTOWrapper createDialog(ArrayList<Integer> userIds);

    DTOWrapper getUnreadCount();

    DTOWrapper deleteDialog(int dialogId);

    DTOWrapper sendMessage(int dialogId, String messageText);

    DTOWrapper getDialogMessages(int dialogId, String query, int offset, int itemPerPage);

    DTOWrapper markAsRead(int dialogId, int messageId);

    DTOWrapper getUserActivity(int dialogId, int userId);

    DTOWrapper setUserDialogStatus(int dialogId, int userId);
}
