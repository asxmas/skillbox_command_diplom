package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.entity.Country;

public class CountryMapper {
    public static Country convertCountryDTOToCountry (CountryDto countryDto) {
        return Country.builder()
                .id(countryDto.getId())
                .title(countryDto.getTitle())
                .build();
    }
}
