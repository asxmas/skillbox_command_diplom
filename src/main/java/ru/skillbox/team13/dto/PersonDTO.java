package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;

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

    @JsonProperty("reg_date")
    private long registrationDate;

    @JsonProperty("birth_date")
    private long birthDate;

    private String email;
    private String phone;
    private String photo;
    private String about;

    @JsonProperty("city")
    private CityDto cityDto;

    @JsonProperty("country")
    private CountryDto countryDto;

    @JsonProperty("messages_permission")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PersonMessagePermission messagesPermission;

    @JsonProperty("last_online_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long lastOnlineTime;

    @JsonProperty("is_blocked")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isBlocked;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonProperty("is_friend") //todo !!!
    Boolean friend;

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
