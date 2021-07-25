package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.NotificationCode;

@Getter
@Setter
@AllArgsConstructor
public class SubscribeResponseDto {

    private NotificationCode type;

    private boolean enable;

    @JsonProperty("type")
    public NotificationCode getType() {
        return type;
    }

    @JsonProperty("notification_type")
    public void setType(NotificationCode type) {
        this.type = type;
    }
}
