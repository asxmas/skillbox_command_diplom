package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    LocalDateTime time;

    @Column(name = "author_id")
    int authorId;

    String title;

    @Column(name = "post_text")
    String postText;

    @Column(name = "is_blocked")
    boolean isBlocked;

}
