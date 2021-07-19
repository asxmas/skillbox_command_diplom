package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Post;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
@RequiredArgsConstructor
public class PostMapper {

//    public static PostDto convertPostToPostDTO(Post post) {
//        return PostDto.builder()
//                .id(post.getId())
//                .author(PersonMapper.convertPersonToPersonDTO(post.getAuthor()))
//                .title(post.getTitle())
//                .comments(new ArrayList<>(CommentMapper.convertSetCommentToSetCommentDTO(Set.copyOf(post.getComments()))))
//                .build();
//    }
//
//    public static Set<PostDto> convertSetPostToSetPostDTO(Set<Post> posts) {
//        if (posts.size() == 0) {
//            return null;
//        }
//        return posts.stream()
//                .map(PostMapper::convertPostToPostDTO)
//                .collect(Collectors.toSet());
//    }
}
