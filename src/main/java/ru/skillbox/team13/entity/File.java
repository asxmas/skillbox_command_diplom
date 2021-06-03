package ru.skillbox.team13.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post_file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "post_id")
    int postId;
    String name;
    String path;
}
