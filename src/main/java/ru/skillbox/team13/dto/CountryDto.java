package ru.skillbox.team13.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountryDto {

    private final int id;
    private final String title;
}
