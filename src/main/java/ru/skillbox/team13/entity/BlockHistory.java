package ru.skillbox.team13.entity;

import lombok.Data;
import ru.skillbox.team13.entity.enums.BlockAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "block_history")
public class BlockHistory {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "person_id", nullable = false)
    private int personId;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private BlockAction action;
}
