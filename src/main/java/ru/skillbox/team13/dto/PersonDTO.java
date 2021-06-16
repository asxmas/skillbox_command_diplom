package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class PersonDTO {

    private final int id;

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("reg_date")
    private final long registrationDate;

    @JsonProperty("birth_date")
    private final long birthDate;

    private final String email;
    private final String phone;
    private final String photo;
    private final String about;
    private final CityDto city;
    private final CountryDto country;

    @JsonProperty("messages_permission")
    private final String messagesPermission;

    @JsonProperty("last_online_time")
    private final long lastOnlineTime;

    @JsonProperty("is_blocked")
    private final boolean isBlocked;
    private final Set<PostDTO> posts;
}
