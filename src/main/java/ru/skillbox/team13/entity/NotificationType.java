package ru.skillbox.team13.entity;

import lombok.Data;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification_type")
public class NotificationType {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "code", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationCode code;

    @Column(name = "name", nullable = false)
    private String name;
}
