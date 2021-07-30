package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "languages")
public class Language {
        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private int id;

        @Column(name = "title", nullable = false)
        private String title;

        public Language(String title) {
                this.title = title;
        }
}
