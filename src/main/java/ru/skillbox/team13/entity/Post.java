package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "author_id", nullable = false)
    private int authorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "post_text", nullable = false)
    private String postText;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;
}
