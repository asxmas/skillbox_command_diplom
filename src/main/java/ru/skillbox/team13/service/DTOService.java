package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.entity.Person;

@Service
@RequiredArgsConstructor
public class DTOService {
    //todo implement ModelMapper / MapStruct
    private final TimeService timeService;

    public PersonDTO convertPersonToPersonDTO(Person p) {
        return PersonDTO.builder()
                .id(p.getId())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .registrationDate(timeService.getTimestamp(p.getRegDate()))
                .birthDate(timeService.getTimestamp(p.getBirthDate()))
                .email(p.getEmail())
                .phone(p.getPhone())
                .photo(p.getPhoto())
                .about(p.getAbout())
                .city(convertCityToCityDTO(p.getCity()))
                .country(convertCountryToCountryDTO(p.getCountry()))
                .messagesPermission(p.getMessagesPermission().name())
                .lastOnlineTime(timeService.getTimestamp(p.getLastOnlineTime()))
                .isBlocked(p.isBlocked()).build();
    }

    private CountryDto convertCountryToCountryDTO(Country c) {
        if (c == null) return null;
        return CountryDto.builder().id(c.getId()).title(c.getTitle()).build();
    }

    private CityDto convertCityToCityDTO(City c) {
        if (c == null) return null;
        return CityDto.builder().id(c.getId()).title(c.getTitle()).build();
    }
}
