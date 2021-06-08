package ru.skillbox.team13.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CountryDto {

    Integer id;
    String title;
}