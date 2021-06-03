package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post_file")
public class PostFile {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "name")
    private String name;

    @Column(name = "path", nullable = false)
    private String path;
}
