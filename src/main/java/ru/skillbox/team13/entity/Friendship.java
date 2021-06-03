package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "status_id")
    private int statusId;

    @Column(name = "src_person_id")
    private int srcPersonId;

    @Column(name = "dst_person_id")
    private int dstPersonId;

}
