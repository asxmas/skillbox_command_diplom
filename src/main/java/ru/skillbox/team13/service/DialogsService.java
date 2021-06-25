package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

import java.util.ArrayList;

public interface DialogsService {

    DTOWrapper getUserDialogs(String query, int offset, int itemPerPage);

    DTOWrapper createDialog(int[] userIds);

    DTOWrapper getUnreadCount();

    DTOWrapper deleteDialog(int dialogId);
}
