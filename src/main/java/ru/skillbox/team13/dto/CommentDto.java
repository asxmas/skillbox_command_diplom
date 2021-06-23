package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {
    int id;

    @JsonProperty("comment_text")
    String text;

    @JsonProperty("post_id")
    String postId; //todo wtf?? "post_id": "string",

    @JsonProperty("parent_id")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer parentId;
    long time;

    @JsonProperty("author_id")
    int authorId;

    @JsonProperty("is_blocked")
    boolean isBlocked;
}
