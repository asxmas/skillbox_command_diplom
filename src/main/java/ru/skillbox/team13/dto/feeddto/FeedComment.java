package ru.skillbox.team13.dto.feeddto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedComment {
    int id;

    @JsonProperty("comment_text")
    String text;

    @JsonProperty("post_id")
    String postId; //todo wtf?? "post_id": "string",

    @JsonProperty("parent_id")
    int parentId;
    long time;

    @JsonProperty("author_id")
    int authorId;

    @JsonProperty("is_blocked")
    boolean isBlocked;
}
