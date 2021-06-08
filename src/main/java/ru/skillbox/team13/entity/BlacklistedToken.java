package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "token_black_list")
public class BlacklistedToken {

        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private int id;

        @Column(name = "token", nullable = false)
        private String token;

        @Column(name = "expired_date", nullable = false)
        private Date expiredDate;
}
