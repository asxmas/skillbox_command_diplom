package ru.skillbox.team13.entiny;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class Message {
    int id;
    LocalDateTime time;
    int authorId;
    int recipientId;
    String messageText;
    String readStatus;
}
