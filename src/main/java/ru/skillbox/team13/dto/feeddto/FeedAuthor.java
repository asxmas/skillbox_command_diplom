package ru.skillbox.team13.dto.feeddto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedAuthor { //todo merge with regular dtos

    int id;
    @JsonProperty("first_name")

    String firstName;
    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("reg_date")
    long regDate;

    @JsonProperty("birth_date")
    long birthDate;
    String email;
    String phone;
    String photo;
    String about;

    FeedCity city;
    FeedCountry country;

   @JsonProperty("messages_permission")
    String messagesPermission;

   @JsonProperty("last_online_time")
    long lastOnlineTime;

   @JsonProperty("is_blocked")
    boolean isBlocked;
}
