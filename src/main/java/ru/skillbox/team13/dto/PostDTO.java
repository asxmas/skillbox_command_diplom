package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class PostDTO {

    @JsonProperty("time")
    private final Timestamp time;
    private final PersonDTO author;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("post_text")
    private final String postText;

    @JsonProperty("likes")
    private final Integer countLikes;

    private final Set<CommentDTO> comments;
    private final Set<TagDTO> tags;

}
