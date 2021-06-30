package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface NotificationService {

    DTOWrapper getAllNotification();

    DTOWrapper getNotificationById(int id, boolean isAllRead);
}
