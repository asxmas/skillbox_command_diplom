package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
//import ru.skillbox.team13.service.PersonService;
import ru.skillbox.team13.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostMapper {
    public static PostDto convertPostToPostDTO(Post post)   {
        return PostDto.builder()
                .id(post.getId())
                .author(PersonMapper.convertPersonToPersonDTO(post.getAuthor()))
                .title(post.getTitle())
                .comments(new ArrayList<>(CommentMapper.convertSetCommentToSetCommentDTO(Set.copyOf(post.getComments()))))
                .build();
    }
    public static Set<PostDto> convertSetPostToSetPostDTO(Set<Post> posts) {
        if (posts.size() == 0)   {
            return null;
        }
        return posts.stream()
                .map(PostMapper::convertPostToPostDTO)
                .collect(Collectors.toSet());
    }
}
