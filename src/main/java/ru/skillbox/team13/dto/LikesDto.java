package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LikesDto {
    private final Object likes;

    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int[] users;
}
