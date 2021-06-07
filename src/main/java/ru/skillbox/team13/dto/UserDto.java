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

        /**
         * формат Request body для регистрации пользователя - POST api/v1/account/register
         * {
         *   "email": "arkady@example.com",
         *   "passwd1": "123456",
         *   "passwd2": "123456",
         *   "firstName": "Аркадий",
         *   "lastName": "Паровозов",
         *   "code": "3675"
         * }
         */
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

        /**
         * формат для возврата данных пользователя, объединяющий User и Person
         * "id": 1,
         * "first_name": "Петр",
         * "last_name": "Петрович",
         * "reg_date": 1559751301818,
         * "birth_date": 1559751301818,
         * "email": "petr@mail.ru",
         * "phone": "89100000000",
         * "photo": "https://...../photos/image123.jpg",
         * "about": "Родился в небольшой, но честной семье",
         * "city": {
         * "id": 1,
         * "title": "Москва"
         * },
         * "country": {
         * "id": 1,
         * "title": "Россия"
         * },
         * "messages_permission": "ALL",
         * "last_online_time": 1559751301818,
         * "is_blocked": false
         * "token" : "oiu25dfpo45ah"
         */
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
