package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "tag", nullable = false, unique = true)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;

    public Tag(String tag) {
        this.tag = tag;
        posts = new HashSet<>();
    }
}
