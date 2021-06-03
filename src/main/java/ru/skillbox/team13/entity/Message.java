package ru.skillbox.team13.entity;

import lombok.Data;
import ru.skillbox.team13.entity.enums.MessageReadStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private LocalDateTime time;

    @Column(name = "author_id", nullable = false)
    private int authorId;

    @Column(name = "recipient_id", nullable = false)
    private int recipientId;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "read_status", nullable = false)
    private MessageReadStatus readStatus;
}
