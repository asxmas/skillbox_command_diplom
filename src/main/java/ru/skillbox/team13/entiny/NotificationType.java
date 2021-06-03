package ru.skillbox.team13.entiny;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "notification_type")
public class NotificationType {
    int id;
    int code;
    String name;
}
