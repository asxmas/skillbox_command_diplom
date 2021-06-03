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
    private int id;
    private LocalDateTime time;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "comment_id")
    private int commentId;

    @Enumerated(EnumType.STRING)
    private Action action;

    public enum Action {
        BLOCK,
        UNBLOCK
    }
}
