package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class PersonDTO {

    private final Integer id;

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    private final long registrationDate;

    @JsonProperty("birth_date")
    private final String birthDate;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("phone")
    private final String phone;

    @JsonProperty("photo")
    private final String photo;

    @JsonProperty("about")
    private final String about;
    private final CityDto city;
    private final CountryDto country;

    @JsonProperty("messages_permission")
    private final String messagesPermission;

    @JsonProperty("last_online_time")
    private final long lastOnlineTime;

    @JsonProperty("is_blocked")
    private final boolean isBlocked;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String token;
}
