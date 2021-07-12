package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private int id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private long birthDate;

    @JsonProperty("email")
    private String email;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("about")
    private String about;

    @JsonProperty("city")
    private CityDto cityDto;

    @JsonProperty("country")
    private CountryDto countryDto;

    @JsonProperty("messages_permission")
    private PersonMessagePermission messagesPermission;

    @JsonProperty("last_online_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long lastOnlineTime;

    @JsonProperty("is_blocked")
    private boolean isBlocked;

    @JsonProperty("country_id")
    private Integer countryId;

    @JsonProperty("town_id")
    private Integer townId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    private long registrationDate;

    public PersonDTO(int id, String firstName, String lastName, LocalDateTime registrationDate, LocalDateTime birthDate,
                     String email, String phone, String photo, String about, int cityId, String cityTitle,
                     int countryId, String countryTitle) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = TimeUtil.getTimestamp(registrationDate);
        this.birthDate = TimeUtil.getTimestamp(birthDate);
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.about = about;
        this.cityDto = new CityDto(cityId, cityTitle);
        this.countryDto = new CountryDto(countryId, countryTitle);
    }

    public PersonDTO(int id) {
        this.id = id;
    }
}
