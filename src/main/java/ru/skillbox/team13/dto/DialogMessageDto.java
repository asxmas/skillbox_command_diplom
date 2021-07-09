package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DialogMessageDto {

    private int id;
    private long time;

    @JsonProperty("author_id")
    private int authorId;

    @JsonProperty("recipient_id")
    private int recipientId;

    @JsonProperty("message_text")
    private String messageText;

    @JsonProperty("read_status")
    private String readStatus;
}
