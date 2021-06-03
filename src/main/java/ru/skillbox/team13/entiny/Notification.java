package ru.skillbox.team13.entiny;

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
    int typeId;
    LocalDateTime sentTime;
    int personId;
    int entityId;
    String contact;
}
