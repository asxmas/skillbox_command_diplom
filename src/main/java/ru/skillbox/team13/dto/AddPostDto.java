package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddPostDto {
    private String title;

    @JsonProperty("post_text")
    private String postText;

    private String[] tags;
}