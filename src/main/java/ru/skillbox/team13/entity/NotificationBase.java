package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "descriminator_type")
@DiscriminatorValue("BASE")
public class NotificationBase {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @JoinColumn(name = "notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationCode notificationType;

    @Column(name = "sent_time", nullable = false)
    private LocalDateTime sentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    private Notified entity;

    @Column(name = "info", nullable = false)
    private String info;
}
