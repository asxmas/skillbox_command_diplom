package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    LocalDateTime time;

    @Column(name = "author_id")
    int authorId;

    @Column(name = "recipeint_id")
    int recipientId;

    @Column(name = "message_text")
    String messageText;

    @Column(name = "read_status")
    String readStatus;
}
