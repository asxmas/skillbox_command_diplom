package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "person")
public class Person extends Notified {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo")
    private String photo;

    @Column(name = "about")
    private String about;

    @Column(name = "deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name = "messages_permission", nullable = false)
    private PersonMessagePermission messagesPermission;

    @Column(name = "last_online_time")
    private LocalDateTime lastOnlineTime;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    //'friends' list
    @OneToMany(mappedBy = "sourcePerson", fetch = FetchType.LAZY)
    private Set<Friendship> requestedFriendships;

    //'friends of' list
    @OneToMany(mappedBy = "destinationPerson", fetch = FetchType.LAZY)
    private Set<Friendship> receivedFriendships;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Message> messagesSent;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    private Set<Message> messagesReceived;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Post> posts;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Like> likes;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Block> blocks;

    @ManyToMany(mappedBy = "persons", fetch = FetchType.LAZY)
    private Set<Dialog> dialogs;
}