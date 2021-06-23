package ru.skillbox.team13.mapping;


import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.entity.Country;

public class CountryMapper {

        public static CountryDto mapToCountryDto(Country country) {
            return new CountryDto(country.getId(), country.getTitle());
        }
}
