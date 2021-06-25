package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.CommentProjection;
import ru.skillbox.team13.entity.projection.LikeCount;
import ru.skillbox.team13.util.TimeUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FeedMapper {

    public static List<PostDto> buildFeed(List<Post> posts, List<LikeCount> likes, List<CommentProjection> comments) {
        return posts.stream().map(p -> PostDto.builder()
                .id(p.getId())
                .timestamp(TimeUtil.getTimestamp(p.getTime()))
                .author(PersonMapper.convertPersonToPersonDTO(p.getAuthor()))
                .title(p.getTitle())
                .text(p.getPostText())
                .isBlocked(p.isBlocked())
                .likes(getLikeCountForPostId(p.getId(), likes))
                .comments(getCommentsForPostId(p.getId(), comments))
                .build()).collect(Collectors.toList());
    }

    private static Integer getLikeCountForPostId(int pId, List<LikeCount> likes) {
        Optional<LikeCount> o = likes.stream().filter(l -> l.getId() == pId).findFirst();
        return o.map(LikeCount::getLikeCount).orElse(0);
    }

    private static List<CommentDto> getCommentsForPostId(int pId, List<CommentProjection> comments) {
        return comments.stream().filter(c -> c.getPostId() == pId)
                .map(f -> CommentDto.builder()
                        .parentId(f.getParentId())
                        .text(f.getText())
                        .id(f.getId())
                        .postId(String.valueOf(f.getPostId())) //todo !! api wants string
                        .time(TimeUtil.getTimestamp(f.getTime()))
                        .authorId(f.getAuthorId())
                        .isBlocked(f.getBlocked())
                        .build())
                .collect(Collectors.toList());
    }
}
