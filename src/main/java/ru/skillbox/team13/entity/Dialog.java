package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "dialog")
@NoArgsConstructor
public class Dialog {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "last_message_id")
    private Message lastMessage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dialog")
    private List<Message> messages;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "dialog2person",
            joinColumns = @JoinColumn(name = "dialog_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "person_id", nullable = false))
    private Set<Person> persons;
}
