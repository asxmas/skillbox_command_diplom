package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.MessageReadStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "message")
public class Message extends Notified {
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Person recipient;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "read_status", nullable = false)
    private MessageReadStatus readStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dialog_id", nullable = false)
    private Dialog dialog;
}
