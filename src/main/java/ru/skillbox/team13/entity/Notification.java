package ru.skillbox.team13.entity;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "type_id")
    private int typeId;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "entity_id")
    private int entityId;
    private String contact;
}
