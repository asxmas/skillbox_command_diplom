package ru.skillbox.team13.entity;
import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "friendship_status")
public class FriendshipStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    LocalDateTime time;
    String name;
    String code;
}
