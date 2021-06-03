package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "block_history")
public class BlockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    LocalDateTime time;

    @Column(name = "person_id")
    int personId;

    @Column(name = "post_id")
    int postId;

    @Column(name = "comment_id")
    int commentId;
    String action;
}
