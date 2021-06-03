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
    private int id;
    private LocalDateTime time;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "parent_id")
    private int parentId;

    @Column(name = "author_id")
    private int authorId;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "is_blocked")
    private boolean isBlocked;


}
