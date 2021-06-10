package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.entity.City;

public class CityMapper {
    public static City convertCityDTOtoCity(CityDto cityDto)    {
        return City.builder()
                .id(cityDto.getId())
                .title(cityDto.getTitle())
                .build();
    }
}
