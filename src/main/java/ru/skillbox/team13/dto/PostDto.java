package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.skillbox.team13.entity.enums.WallPostType;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private int id;

    private Long time;

    @JsonIgnore //internal use only
    private int authorID;

    private PersonCompactDto author;

    private String title;

    @JsonProperty("post_text")
    private String text;

    @JsonProperty("is_blocked")
    private Boolean blocked;

    private Integer likes;

    private List<String> tags; //todo maybe TagDTO

    @JsonProperty("my_like")
    private Boolean likedByMe;

    private List<CommentDto> comments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private WallPostType type;

    public PostDto(int id, LocalDateTime time, int authorID, String firstName, String lastName, String photo,
                   LocalDateTime lastOnline, String title, String text, Boolean blocked, Integer likes, Boolean likedByMe) {
        this.id = id;
        this.time = TimeUtil.getTimestamp(time);
        this.authorID = authorID;
        this.author = new PersonCompactDto(authorID, firstName, lastName, photo, TimeUtil.getTimestamp(lastOnline));
        this.title = title;
        this.text = text;
        this.blocked = blocked;
        this.likes = likes;
        this.tags = new ArrayList<>();
        this.likedByMe = likedByMe;
        this.comments = new ArrayList<>();
        this.type = WallPostType.POSTED; //todo temporary
    }
}
