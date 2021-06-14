package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.entity.City;

public class CityMapper {
    public static City convertCityDTOtoCity(CityDto cityDto)    {
        City city = new City();
        int id = cityDto.getId();
        String title = cityDto.getTitle();
        city.setId(id);
        city.setTitle(title);
        return city;
    }
}
