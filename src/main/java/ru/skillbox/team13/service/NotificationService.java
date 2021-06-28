package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface NotificationService {

    DTOWrapper getAllNotification(int offset, int limit);

    DTOWrapper getNotificationById(int offset, int limit, int id, boolean isAllRead);
}
