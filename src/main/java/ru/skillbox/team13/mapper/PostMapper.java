package ru.skillbox.team13.mapper;

import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.LikeCount;
import ru.skillbox.team13.util.TimeUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostMapper {

    @Deprecated
    public static List<PostDto> combinePostsLikesComments(List<Post> posts, List<LikeCount> likes, List<CommentDto> comments) {
        return posts.stream().map(p -> buildPostDto(p, likes, comments)).collect(Collectors.toList());
    }

    @Deprecated
    public static PostDto buildPostDto(Post p, List<LikeCount> likes, List<CommentDto> comments) {
        return PostDto.builder()
                .id(p.getId())
                .timestamp(TimeUtil.getTimestamp(p.getTime()))
                .author(PersonMapper.convertPersonToPersonDTO(p.getAuthor()))
                .title(p.getTitle())
                .text(p.getPostText())
                .isBlocked(p.isBlocked())
                .likes(getLikeCountForPostId(p.getId(), likes))
                .comments(filterCommentsByPost(p.getId(), comments))
                .build();
    }

    private static Integer getLikeCountForPostId(int pId, List<LikeCount> likes) {
        Optional<LikeCount> o = likes.stream().filter(l -> l.getId() == pId).findFirst();
        return o.map(LikeCount::getLikeCount).orElse(0);
    }

    private static List<CommentDto> filterCommentsByPost(int pId, List<CommentDto> comments) {
        return comments.stream().filter(c -> c.getPostId() == pId)
                .collect(Collectors.toList());
    }
}
