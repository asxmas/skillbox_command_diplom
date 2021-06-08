package ru.skillbox.team13.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Builder
public class CityDto {

    Integer id;
    String title;
}
