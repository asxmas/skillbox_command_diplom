package ru.skillbox.team13.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.NotificationCode;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "subscription")
public class Subscription {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person", nullable = false)
    private Person person;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationCode type;
}
