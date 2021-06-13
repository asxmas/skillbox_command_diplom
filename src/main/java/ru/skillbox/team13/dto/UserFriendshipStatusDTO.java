package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserFriendshipStatusDTO {
    @JsonProperty("user_id")
    private final int userId;
    private final String status;
}
