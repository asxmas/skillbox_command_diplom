package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditPersonDto {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private Long birthDate;

    private String phone;

    @JsonProperty("photo_id")
    private String photo;

    private String about;

    @JsonProperty("town_id")
    private Integer cityId;

    @JsonProperty("country_id")
    private Integer countryId;

    @JsonProperty("messages_permission")
    private PersonMessagePermission permission;
}
