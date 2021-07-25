package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LongpollParamDto {

    @JsonProperty("ts")
    private long timestamp;

    @JsonProperty("pts")
    private int points;

    @JsonProperty("preview_length")
    private int previewLength;

    private int onlines;

    @JsonProperty("events_limit")
    private int eventsLimit;

    @JsonProperty("msgs_limit")
    private int messagesLimit;

    @JsonProperty("max_msg_id")
    private int maxMessageId;
}
