package ru.skillbox.team13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlatformValuesDto {
    private final String[] cities;
    private final String[] countries;
    private final String[] languages;
}
