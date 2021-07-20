package ru.skillbox.team13.util;

import ru.skillbox.team13.entity.enums.NotificationCode;

import static ru.skillbox.team13.entity.enums.NotificationCode.*;

public class MapNotification {

    public static Integer mapNotificationCode(NotificationCode note) {
        switch (note) {
            case POST : return 1;
            case POST_COMMENT : return 2;
            case COMMENT_COMMENT : return 3;
            case FRIEND_REQUEST : return 4;
            case MESSAGE : return 5;
        }
      return null;
    }
}
