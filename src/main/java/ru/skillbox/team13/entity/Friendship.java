package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "status_id", nullable = false)
    private int statusId;

    @Column(name = "src_person_id", nullable = false)
    private int srcPersonId;

    @Column(name = "dst_person_id", nullable = false)
    private int dstPersonId;

}
