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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "src_person_id", nullable = false)
    private Person sourcePerson;

    //person who was 'friended' by someone
    @ManyToOne(fetch = FetchType.EAGER)  //todo test
    @JoinColumn(name = "dst_person_id", nullable = false)
    private Person destinationPerson;

    public Friendship(FriendshipStatus status, Person sourcePerson, Person destinationPerson) {
        this.status = status;
        this.sourcePerson = sourcePerson;
        this.destinationPerson = destinationPerson;
    }
}
