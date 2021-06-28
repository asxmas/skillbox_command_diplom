package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class notificationDto {

    int id;
    @JsonProperty("type_id")
    int typeId;
    @JsonProperty("sent_time")
    LocalDateTime sentTime;
    @JsonProperty("entity_id")
    int entityId;
    String info;
}
