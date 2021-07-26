package ru.skillbox.team13.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class SubscribeNotificationDto {
    private String message;
}
