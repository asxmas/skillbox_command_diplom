package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.service.PersonService;
import ru.skillbox.team13.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostMapper {
    public static PostDTO convertPostToPostDTO(Post post)   {
        return PostDTO.builder().postText(post.getPostText())
                .id(post.getId())
                .author(PersonMapper.convertPersonToPersonDTO(post.getAuthor()))
                .title(post.getTitle())
                .postText(post.getPostText())
                .countLikes(post.getLikes().size())
                .comments(CommentMapper.convertSetCommentToSetCommentDTO(post.getComments()))
                .tags(TagMapper.convertSetTagToSetTagDTO(post.getTags())).build();
    }
    public static Set<PostDTO> convertSetPostToSetPostDTO(Set<Post> posts) {
        if (posts.size() == 0)   {
            return null;
        }
        return posts.stream()
                .map(PostMapper::convertPostToPostDTO)
                .collect(Collectors.toSet());
    }
}
