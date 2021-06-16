package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.util.TimeUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
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
                .posts(PostMapper.convertSetPostToSetPostDTO(p.getPosts()))
                .isBlocked(p.isBlocked()).build();
    }

    public static Person convertPersonDTOToPerson (PersonDTO personDTO)  {
        String firstName = personDTO.getFirstName();
        String lastName = personDTO.getLastName();
        LocalDateTime birthDate = TimeUtil.toLocalDateTime(personDTO.getBirthDate());
        LocalDateTime regDate = TimeUtil.toLocalDateTime(personDTO.getRegistrationDate());
        String email = personDTO.getEmail();
        String phone = personDTO.getPhone();
        String photo = personDTO.getPhoto();
        String about = personDTO.getAbout();
        Set<PostDTO> posts = personDTO.getPosts();
        City city = CityMapper.convertCityDTOtoCity(personDTO.getCity());
        Country country = CountryMapper.convertCountryDTOToCountry(personDTO.getCountry());
        PersonMessagePermission personMessagePermission = PersonMessagePermission.valueOf(personDTO.getMessagesPermission());
        LocalDateTime lastOnlineTime = TimeUtil.toLocalDateTime(personDTO.getLastOnlineTime());
        boolean isBlocked = personDTO.isBlocked();
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(birthDate);
        person.setRegDate(regDate);
        person.setEmail(email);
        person.setPhone(phone);
        person.setPhoto(photo);
        person.setAbout(about);
        person.setCity(city);
        person.setCountry(country);
        person.setMessagesPermission(personMessagePermission);
        person.setLastOnlineTime(lastOnlineTime);
        person.setBlocked(isBlocked);
        person.setPosts(PostMapper.convertSetPostDTOToSetPost(posts));
        return person;
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
