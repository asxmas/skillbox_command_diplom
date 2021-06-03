package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "e_mail")
    private String email;

    private String phone;
    private String password;
    private String photo;
    private String about;
    private String town;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Enumerated(EnumType.STRING)
    @Column(name = "messages_permission")
    private MessagePermission messagesPermission;

    @Column(name = "last_online_time")
    private LocalDateTime lastOnlineTime;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    public enum MessagePermission {
        ALL, FRIENDS
    }
}
