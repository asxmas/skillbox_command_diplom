package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "friendship")
public class Friendship {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private FriendshipStatus status;

    //person who 'friended' someone
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "src_person_id", nullable = false)
    private Person fromPerson;

    //person who was 'friended' by someone
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dst_person_id", nullable = false)
    private Person toPerson;

    public Friendship(FriendshipStatus status, Person fromPerson, Person toPerson) {
        this.status = status;
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
    }
}
