package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification_type")
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private Code code;

    private String name;

    public enum Code {
        POST,
        POST_COMMENT,
        COMMENT_COMMENT,
        FRIEND_REQUEST,
        MESSAGE
    }
}
