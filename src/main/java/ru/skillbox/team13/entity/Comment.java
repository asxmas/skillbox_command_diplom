package ru.skillbox.team13.entity;

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
@Table(name = "post_comment")
public class Comment extends Notified {

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Comment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToMany(mappedBy = "parent")
    private Set<Comment> childComments;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Like> likes;

    public Comment(LocalDateTime time, Post post, Person author, String commentText, boolean isBlocked) {
        this.time = time;
        this.post = post;
        this.author = author;
        this.commentText = commentText;
        this.isBlocked = isBlocked;
    }
}
