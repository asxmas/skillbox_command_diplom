package ru.skillbox.team13.entity.projection;

import java.time.LocalDateTime;

public interface CommentProjection {
    Integer getId();

    String getText();

    Integer getPostId();

    Integer getParentId();

    LocalDateTime getTime();

    Integer getAuthorId();

    Boolean getBlocked();
}
