package ru.skillbox.team13.util;

import ru.skillbox.team13.entity.enums.NotificationCode;

import static ru.skillbox.team13.entity.enums.NotificationCode.*;

public class MapNotification {

    public static int mapNotificationCode(NotificationCode note) {
        if(note.equals(POST)) {
            return 1;
        }
        if(note.equals(POST_COMMENT)) {
            return 2;
        }
        if(note.equals(COMMENT_COMMENT)) {
            return 3;
        }
        if(note.equals(FRIEND_REQUEST)) {
            return 4;
        }
        if(note.equals(MESSAGE)) {
            return 5;
        }
      return 1;
    }
}
