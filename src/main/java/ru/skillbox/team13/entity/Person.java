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
    int id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "reg_date")
    LocalDateTime regDate;

    @Column(name = "birth_date")
    LocalDateTime birthDate;

    String phone;
    String password;
    String photo;
    String about;
    String town;

    @Column(name = "confirmation_code")
    String confirmationCode;

    @Column(name = "is_approved")
    boolean isApproved;

    @Column(name = "messages_permission")
    String messagesPermission;

    @Column(name = "last_online_time")
    LocalDateTime lastOnlineTime;

    @Column(name = "is_blocked")
    boolean isBlocked;

}
