package ru.skillbox.team13.mapper;

import lombok.RequiredArgsConstructor;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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
        Post post = new Post();
        LocalDateTime time = postDTO.getTime().toLocalDateTime();
        Person author = PersonMapper.convertPersonDTOToPerson(postDTO.getAuthor());
        String title = postDTO.getTitle();
        String postText = postDTO.getPostText();
        Set<Like> likes = postDTO.getLikes();
        Set<Comment> comments = CommentMapper.convertSetCommentDTOToSetComment(postDTO.getComments());
        post.setTime(time);
        post.setAuthor(author);
        post.setTitle(title);
        post.setPostText(postText);
        post.setLikes(likes);
        post.setComments(comments);
        return post;
    }

    public static Set<PostDTO> convertSetPostToSetPostDTO(Set<Post> posts) {
        return posts.stream()
                .map(PostMapper::convertPostToPostDTO)
                .collect(Collectors.toSet());
    }

    public static Set<Post> convertSetPostDTOToSetPost(Set<PostDTO> posts) {
        return posts.stream()
                .map(PostMapper::convertPostDTOtoPost)
                .collect(Collectors.toSet());
    }
}
