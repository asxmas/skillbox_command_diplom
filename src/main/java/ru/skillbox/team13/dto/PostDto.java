package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDto {

    int id;
    long timestamp;
    PersonDTO author;
    String title;

    @JsonProperty("post_text")
    String text;

    @JsonProperty("is_blocked")
    boolean isBlocked;

    int likes;

    List<CommentDto> comments;




}
