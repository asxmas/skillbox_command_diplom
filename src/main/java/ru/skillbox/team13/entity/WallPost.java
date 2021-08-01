package ru.skillbox.team13.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.WallPostType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Deprecated
@Table(name = "wall_post")
public class WallPost extends Post {
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private WallPostType type;
}
