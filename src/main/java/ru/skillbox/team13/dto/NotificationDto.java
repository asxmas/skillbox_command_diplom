package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {

    private int id;
    @JsonProperty("type_id")
    private int typeId;
    @JsonProperty("sent_time")
    private long sentTime;
    @JsonProperty("entity_id")
    private int entityId;
    String info;
}
