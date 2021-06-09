package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.util.SetUtil;

import java.sql.Timestamp;

@RequiredArgsConstructor
public class PostMapper {
    public static PostDTO convertPostToPostDTO(Post post)   {
        return PostDTO.builder().postText(post.getPostText())
                .time(Timestamp.valueOf(post.getTime()))
                .author(PersonMapper.convertPersonToPersonDTO(post.getAuthor()))
                .title(post.getTitle())
                .postText(post.getPostText())
                .countLikes(post.getLikes().size())
                .comments(SetUtil.convertSetCommentToSetCommentDTO(post.getComments()))
                .tags(SetUtil.convertSetTagToSetTagDTO(post.getTags())).build();

    }
}
