package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentDto {
    private int id;

    @JsonProperty("comment_text")
    private String text;

    @JsonProperty("post_id")
    private Integer postId;

    @JsonProperty("parent_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer parentId;
    private long time;

    @JsonProperty("author_id")
    private int authorId;

    @JsonProperty("is_blocked")
    private boolean isBlocked;

    public CommentDto(Integer id, Integer postId, Integer parentId, String text, LocalDateTime time, Integer authorId, Boolean blocked) {
        this.id = id;
        this.postId = postId;
        this.parentId = parentId;
        this.text = text;
        this.time = TimeUtil.getTimestamp(time);
        this.authorId = authorId;
        this.isBlocked = blocked;
    }
}
