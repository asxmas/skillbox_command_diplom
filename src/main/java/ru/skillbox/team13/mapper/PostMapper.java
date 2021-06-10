package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class PostMapper {
    public static PostDTO convertPostToPostDTO(Post post)   {
        return PostDTO.builder().postText(post.getPostText())
                .time(Timestamp.valueOf(post.getTime()))
                .author(PersonMapper.convertPersonToPersonDTO(post.getAuthor()))
                .title(post.getTitle())
                .postText(post.getPostText())
                .countLikes(post.getLikes().size())
                .comments(CommentMapper.convertSetCommentToSetCommentDTO(post.getComments()))
                .tags(TagMapper.convertSetTagToSetTagDTO(post.getTags())).build();
    }
    public static Post convertPostDTOtoPost (PostDTO postDTO)   {
        return Post.builder()
                .time(postDTO.getTime().toLocalDateTime())
                .author(PersonMapper.convertPersonDTOToPerson(postDTO.getAuthor()))
                .title(postDTO.getTitle())
                .postText(postDTO.getPostText())
                .likes(postDTO.getLikes())
                .comments(CommentMapper.convertSetCommentDTOToSetComment(postDTO.getComments()))
                .build();
    }
}
