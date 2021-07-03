package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;

@Getter
public class CommentDto {
    private final int id;

    @JsonProperty("comment_text")
    private final String text;

    @JsonProperty("post_id")
    private final Integer postId;

    @JsonProperty("parent_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer parentId;
    private final long time;

    @JsonProperty("author_id")
    private final int authorId;

    @JsonProperty("is_blocked")
    private final boolean isBlocked;

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
