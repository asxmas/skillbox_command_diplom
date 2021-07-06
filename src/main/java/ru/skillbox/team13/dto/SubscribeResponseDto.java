package ru.skillbox.team13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.team13.entity.enums.NotificationCode;

@Getter
@Setter
public class SubscribeResponseDto {
    private NotificationCode type;
    private boolean enable;
}
