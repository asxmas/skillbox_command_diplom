package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.team13.entity.Person;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DialogMessageDto {

    private int id;
    private long time;

    @JsonProperty("author")
    private Integer authorId;

    @JsonProperty("recipient")
    private PersonDTO recipient;

    @JsonProperty("message_text")
    private String messageText;

    @JsonProperty("read_status")
    private String readStatus;

    @JsonProperty("isSentByMe")
    private Boolean isSentByMe;
}
