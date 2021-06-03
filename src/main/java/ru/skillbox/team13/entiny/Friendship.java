package ru.skillbox.team13.entiny;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post_comment")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int statusID;
    int srcPersonId;
    int dstPersonId;

}
