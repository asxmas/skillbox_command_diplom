package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonCompactDto {

    private int id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String photo;

    @JsonProperty("last_online_time")
    private Long lastOnlineTime;

    public PersonCompactDto(int id, String firstName, String lastName, String photo, LocalDateTime time) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.lastOnlineTime = TimeUtil.getTimestamp(time);
    }

    public PersonCompactDto(int id) {
        this.id = id;
    }
}
