package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private int id;
    private long timestamp;
    private PersonDTO author;
    private String title;


    @JsonProperty("post_text")
    private String text;

    @JsonProperty("is_blocked")
    private boolean isBlocked;

    private int likes;

    private List<CommentDto> comments;

    public PostDto(int id, LocalDateTime ldt, int authorId, String title, String text, boolean isBlocked, int likes) {
        this.id = id;
        this.timestamp = TimeUtil.getTimestamp(ldt);
        this.author = new PersonDTO(authorId);
        this.title = title;
        this.text = text;
        this.isBlocked = isBlocked;
        this.likes = likes;
    }
}
