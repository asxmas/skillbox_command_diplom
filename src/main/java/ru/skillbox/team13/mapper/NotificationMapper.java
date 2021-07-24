package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.NotificationDto;
import ru.skillbox.team13.entity.Notification;
import ru.skillbox.team13.util.MapNotification;
import ru.skillbox.team13.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {

    public static DTOWrapper mapNotifications(List<Notification> notificationList, int offset, int limit) {

        List<NotificationDto> noteList = new ArrayList<>();
        notificationList.forEach(n -> {
            noteList.add(mapNotification(n));
        });
        return WrapperMapper.wrap(noteList, noteList.size(), offset, limit, true);
    }

    public static NotificationDto mapNotification(Notification n) {
        NotificationDto noteDto = new NotificationDto();
        noteDto.setId(n.getId());
        noteDto.setTypeId(MapNotification.mapNotificationCode(n.getNotificationType()));
        noteDto.setSentTime(TimeUtil.getTimestamp(n.getSentTime()));
        noteDto.setInfo(n.getInfo());
        return noteDto;
    }
}
