package ru.skillbox.team13.entity;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "type_id", nullable = false)
    private int typeId;

    @Column(name = "sent_time", nullable = false)
    private LocalDateTime sentTime;

    @Column(name = "person_id", nullable = false)
    private int personId;

    @Column(name = "entity_id", nullable = false)
    private int entityId;

    @Column(name = "contact", nullable = false)
    private String contact;
}
