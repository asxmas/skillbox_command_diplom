package ru.skillbox.team13.service.impl;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.service.DialogsService;

import java.util.ArrayList;

public class DialogsServiceImpl implements DialogsService {


    @Override
    public DTOWrapper getDialogs(String query, int offset, int itemPerPage) {
        return null;
    }

    @Override
    public DTOWrapper createDialog(ArrayList<Integer> userIds) {
        return null;
    }

    @Override
    public DTOWrapper getUnreadCount() {
        return null;
    }

    @Override
    public DTOWrapper deleteDialog(int dialogId) {
        return null;
    }
}
