package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.entity.enums.UserType;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum UserDto {;

    //данные сущности User
    private interface Email { @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+.+.[A-Za-z]+") String getEmail(); }
    private interface FirstPassword { @Size(min=2, max=50) String getFirstPassword(); }
    private interface SecondPassword { @Size(min=2, max=50) String getSecondPassword(); }
    private interface Code { @Size(min=4, max=6) String getCode(); }
    private interface Name { @Pattern(regexp = "^[А-Яа-яA-Za-z0-9_-]+") String getName(); }
    private interface PersonId { Integer getPersonId(); }
    private interface RegDate { Long getRegDate(); }
    private interface Type { UserType getType(); }
    private interface IsApproved { Boolean isApproved(); }

    //данные сущности Person
    private interface FirstName { @Pattern(regexp = "^[А-Яа-яA-Za-z]+") String getFirstName(); }
    private interface LastName { @Pattern(regexp = "^[А-Яа-яA-Za-z]+") String getLastName(); }
    private interface BirthDate { Long getBirthDate(); }
    private interface IsBlocked { Boolean getIsBlocked(); }
    private interface Phone { String getPhone(); }
    private interface Photo { String getPhoto(); }
    private interface AboutText { String getAbout(); }
    private interface City { CityDto getCity(); }
    private interface Country { CountryDto getCountry(); }
    private interface Permissions { PersonMessagePermission getMessagesPermissions(); }
    private interface LastOnlineTime { Long getLastOnlineTime(); }

    //токен
    private interface Token {String getToken();}

    public enum Request{;

        @Getter
        public static class Register implements Email, FirstPassword, SecondPassword, FirstName, LastName, Code {
            String email;
            @JsonProperty("passwd1")
            String firstPassword;
            @JsonProperty("passwd2")
            String secondPassword;
            String firstName;
            String lastName;
            String code;
        }
    }

    public enum Response {;

        @Getter
        @Builder
        public static class AuthPerson implements PersonId, FirstName, LastName, RegDate, BirthDate,
                Email, Phone, Photo, AboutText, City, Country, Permissions, LastOnlineTime, IsBlocked, Token {
            @JsonProperty("id")
            Integer personId;
            @JsonProperty("first_name")
            String firstName;
            @JsonProperty("last_name")
            String lastName;
            @JsonProperty("reg_date")
            Long regDate;
            @JsonProperty("birth_date")
            Long birthDate;
            String email;
            String phone;
            String photo;
            String about;
            CityDto city;
            CountryDto country;
            @JsonProperty("messages_permissions")
            PersonMessagePermission messagesPermissions;
            @JsonProperty("last_online_time")
            Long lastOnlineTime;
            @JsonProperty("is_blocked")
            Boolean isBlocked;
            String token;
        }
    }
}
