package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendshipStatusCode code;

    //person who 'friended' someone
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "src_person_id", nullable = false)
    private Person sourcePerson;

    //person who was 'friended' by someone
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dst_person_id", nullable = false)
    private Person destinationPerson;

    public Friendship(LocalDateTime time, String name, FriendshipStatusCode code,
                      Person sourcePerson, Person destinationPerson) {
        this.time = time;
        this.name = name;
        this.code = code;
        this.sourcePerson = sourcePerson;
        this.destinationPerson = destinationPerson;
    }
}
