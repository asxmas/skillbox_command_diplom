package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.skillbox.team13.entity.Like;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class PostDTO {

    private final Integer id;

    private final Timestamp time;
    private final Integer authorId;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("post_text")
    private final String postText;

    @JsonProperty("count_likes")
    private final Integer countLikes;

    private final Set<CommentDTO> comments;
    private final Set<TagDTO> tags;
    private final Set<LikeDto> likes;

}
