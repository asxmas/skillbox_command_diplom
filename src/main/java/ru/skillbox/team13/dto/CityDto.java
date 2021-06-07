package ru.skillbox.team13.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CityDto {

    private final int id;
    private final String title;
}
