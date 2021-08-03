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
    private Long birthDate; //todo front sends as '2000-01-01T00:00:00+03:00' (string)

    private String phone;

    @JsonProperty("photo_id")
    private String photo;

    private String about;

    private String city;

    private String country;

    @JsonProperty("messages_permission") // unused??
    private PersonMessagePermission permission;
}
