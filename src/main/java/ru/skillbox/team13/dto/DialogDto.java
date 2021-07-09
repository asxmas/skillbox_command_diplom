package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DialogDto {

    private Integer id;

    @JsonProperty("unread_count")
    private Integer unreadCount;

    @JsonProperty("last_message")
    private DialogMessageDto lastMessage;
}