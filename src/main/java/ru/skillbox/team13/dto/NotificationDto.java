package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {

    int id;
    @JsonProperty("type_id")
    int typeId;
    @JsonProperty("sent_time")
    long sentTime;
    @JsonProperty("entity_id")
    int entityId;
    String info;
}
