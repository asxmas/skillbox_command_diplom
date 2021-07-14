package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostCommentDto {

    @JsonProperty("parent_id")
    int parentId;
    @JsonProperty("comment_text")
    String commentText;
    int id;
    @JsonProperty("post_id")
    int postId;
    long time;
    @JsonProperty("author_id")
    int authorId;
    @JsonProperty("is_blocked")
    boolean isBlocked;

}
