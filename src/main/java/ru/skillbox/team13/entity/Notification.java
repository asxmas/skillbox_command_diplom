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
    int id;

    @Column(name = "type_id")
    int typeId;

    @Column(name = "sent_time")
    LocalDateTime sentTime;

    @Column(name = "person_id")
    int personId;

    @Column(name = "entity_id")
    int entityId;
    String contact;
}
