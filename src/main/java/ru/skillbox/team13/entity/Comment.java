package ru.skillbox.team13.entity;

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

    @Column(name = "post_id")
    int postId;

    @Column(name = "parent_id")
    int parentId;

    @Column(name = "author_id")
    int authorId;

    @Column(name = "comment_text")
    String commentText;

    @Column(name = "is_blocked")
    boolean isBlocked;


}
