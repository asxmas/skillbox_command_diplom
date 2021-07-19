package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private int id;

    @JsonProperty("comment_text")
    private String text;

    @JsonProperty("post_id") //todo unused???
    private Integer postId;

    @JsonProperty("parent_id")
    @JsonInclude(JsonInclude.Include.NON_NULL) //todo maybe always should be present
    private Integer parentId;

    private Long time;

    @JsonIgnore //internal use only
    private int authorID;

    private PersonCompactDto author;

    @JsonProperty("is_blocked")
    private Boolean blocked;

    @JsonProperty("sub_comments")
    private List<CommentDto> comments;

    @JsonProperty("my_like")
    private Boolean likedByMe;

    public CommentDto(int id, String text, Integer postId, Integer parentId, LocalDateTime time, int authorID,
                        String firstName, String lastName, String photo, LocalDateTime lastOnline, Boolean blocked,
                      Boolean likedByMe) {
        this.id = id;
        this.text = text;
        this.postId = postId;
        this.parentId = parentId;
        this.time = TimeUtil.getTimestamp(time);
        this.authorID = authorID;
        this.author = new PersonCompactDto(authorID, firstName, lastName, photo, TimeUtil.getTimestamp(lastOnline));
        this.blocked = blocked;
        this.comments = new ArrayList<>();
        this.likedByMe = likedByMe;
    }
}
