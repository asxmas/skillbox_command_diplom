package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
public class PersonDTO {

    private final Integer id;

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("reg_date")
    private final long registrationDate;

    @JsonProperty("birth_date_LocalDateTime")
    private LocalDateTime birthDateLDT;

    @JsonProperty("birth_date")
    private Timestamp birthDate;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("phone")
    private final String phone;

    @JsonProperty("photo")
    private final String photo;

    @JsonProperty("about")
    private final String about;

    @JsonProperty("city")
    private CityDto cityDto;

    @JsonProperty("country")
    private CountryDto countryDto;

    @JsonProperty("messages_permission")
    private final PersonMessagePermission messagesPermission;

    @JsonProperty("last_online_time")
    private final long lastOnlineTime;

    @JsonProperty("is_blocked")
    private final boolean isBlocked;

    @JsonProperty("country_id")
    private final Integer countryId;

    @JsonProperty("town_id")
    private final Integer townId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String token;


}
