package ru.skillbox.team13.entiny;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    LocalDateTime time;
    int postId;
    int parentId;
    int authorId;
    String commentText;
    boolean isBlocked;


}
