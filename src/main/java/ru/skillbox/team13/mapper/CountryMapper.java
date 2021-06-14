package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.entity.Country;

public class CountryMapper {
    public static Country convertCountryDTOToCountry (CountryDto countryDto) {
        Country country = new Country();
        int id = countryDto.getId();
        String title = countryDto.getTitle();
        country.setId(id);
        country.setTitle(title);
        return country;
    }
}
