package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.util.TimeUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@RequiredArgsConstructor
public class PersonMapper {

    public static PersonDTO convertPersonToPersonDTO(Person p) {
        return PersonDTO.builder()
                .id(p.getId())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .registrationDate(TimeUtil.getTimestamp(p.getRegDate()))
                .birthDate(TimeUtil.getTimestamp(p.getBirthDate()))
                .email(p.getEmail())
                .phone(p.getPhone())
                .photo(p.getPhoto())
                .about(p.getAbout())
                .city(convertCityToCityDTO(p.getCity()))
                .country(convertCountryToCountryDTO(p.getCountry()))
                .messagesPermission(p.getMessagesPermission().name())
                .lastOnlineTime(TimeUtil.getTimestamp(p.getLastOnlineTime()))
                .isBlocked(p.isBlocked()).build();
    }

    public static Person convertPersonDTOToPerson (PersonDTO personDTO)  {
        return Person.builder()
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .birthDate(TimeUtil.toLocalDateTime(personDTO.getBirthDate()))
                .regDate(TimeUtil.toLocalDateTime(personDTO.getRegistrationDate()))
                .email(personDTO.getEmail())
                .phone(personDTO.getPhone())
                .photo(personDTO.getPhoto())
                .about(personDTO.getAbout())
                .city(CityMapper.convertCityDTOtoCity(personDTO.getCity()))
                .country(CountryMapper.convertCountryDTOToCountry(personDTO.getCountry()))
                .messagesPermission(PersonMessagePermission.valueOf(personDTO.getMessagesPermission()))
                .lastOnlineTime(TimeUtil.toLocalDateTime(personDTO.getLastOnlineTime()))
                .isBlocked(personDTO.isBlocked())
                .build();
    }

    private static CountryDto convertCountryToCountryDTO(Country c) {
        if (c == null) return null;
        return CountryDto.builder().id(c.getId()).title(c.getTitle()).build();
    }

    private static CityDto convertCityToCityDTO(City c) {
        if (c == null) return null;
        return CityDto.builder().id(c.getId()).title(c.getTitle()).build();
    }
}
