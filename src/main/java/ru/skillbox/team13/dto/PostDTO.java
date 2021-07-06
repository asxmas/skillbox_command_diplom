package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class PostDTO {

    private final Integer id;

    private PersonDTO author;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("post_text")
    private final String postText;

    @JsonProperty("count_likes")
    private final Integer countLikes;

    private final Set<CommentDTO> comments;
    private final Set<TagDTO> tags;
    private final Set<LikeDto> likes;
    private final LocalDateTime time;

}
