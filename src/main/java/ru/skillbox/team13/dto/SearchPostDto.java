package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SearchPostDto {

    String text;

    @JsonProperty("date_from")
    Long timestampFrom;

    @JsonProperty("date_to")
    Long timestampTo;

    String author;

    String[] tags;
}
