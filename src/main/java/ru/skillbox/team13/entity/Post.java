package ru.skillbox.team13.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "post")
@Inheritance(strategy = InheritanceType.JOINED)
public class Post extends Notified {
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "post_text", nullable = false)
    private String postText;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @OneToMany(mappedBy = "postOrComment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Like> likes;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "post2tag",
            joinColumns = @JoinColumn(name = "post_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false))
    private Set<Tag> tags;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Attachment> attachments;
}
