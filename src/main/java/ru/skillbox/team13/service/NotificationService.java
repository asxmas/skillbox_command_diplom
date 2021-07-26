package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.Comment;

public interface NotificationService {

    DTOWrapper getAllNotification();

    DTOWrapper getNotificationById(int id, boolean isAllRead);

}
