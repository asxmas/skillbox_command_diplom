package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification_type")
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int code;
    String name;
}
