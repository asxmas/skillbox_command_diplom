package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_comment")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "parent_id")
    private int parentId;

    @Column(name = "author_id", nullable = false)
    private int authorId;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;


}
