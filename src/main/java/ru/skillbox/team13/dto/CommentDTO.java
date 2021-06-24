package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Builder
public class CommentDTO {

    private final Integer id;

    @JsonProperty("parent_id")
    private final Integer parentId;

    @JsonProperty("comment_text")
    private final String commentText;

    @JsonProperty("time")
    private final Timestamp time;

}
