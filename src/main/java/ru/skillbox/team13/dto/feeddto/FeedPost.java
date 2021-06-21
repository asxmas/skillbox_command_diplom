package ru.skillbox.team13.dto.feeddto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FeedPost {  //todo merge with regular dtos

    int id;
    long timestamp;
    FeedAuthor author;
    String title;

    @JsonProperty("post_text")
    String text;

    @JsonProperty("is_blocked")
    boolean isBlocked;

    int likes;

    List<FeedComment> comments;




}
