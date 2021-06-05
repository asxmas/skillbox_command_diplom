package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


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

    @Column(name = "unread_count")
    private int unreadCount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "last_message_id")
    private Message lastMessage;
}
