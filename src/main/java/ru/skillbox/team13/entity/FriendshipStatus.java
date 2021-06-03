package ru.skillbox.team13.entity;

import lombok.Data;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "friendship_status")
public class FriendshipStatus {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendshipStatusCode code;
}
