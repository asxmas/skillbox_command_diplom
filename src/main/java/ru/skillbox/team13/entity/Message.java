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
    private int id;
    private LocalDateTime time;

    @Column(name = "author_id")
    private int authorId;

    @Column(name = "recipient_id")
    private int recipientId;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "read_status")
    private ReadStatus readStatus;

    public enum ReadStatus {
        SENT, READ
    }
}
