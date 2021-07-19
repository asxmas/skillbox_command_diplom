package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCommentDto {

    @JsonProperty("parent_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer parentId;

    @JsonProperty("comment_text")
    private String commentText;
}
