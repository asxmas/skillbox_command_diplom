package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.entity.City;

public class CityMapper {

    public static CityDto mapToCityDto(City city) {
        return new CityDto(city.getId(), city.getTitle());
    }
}
