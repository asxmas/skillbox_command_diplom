package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
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

    public NotificationType(NotificationCode code, String name) {
        this.code = code;
        this.name = name;
    }
}
