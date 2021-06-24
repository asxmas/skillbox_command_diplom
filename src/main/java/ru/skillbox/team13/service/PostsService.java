package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.mapper.PostMapper;
import ru.skillbox.team13.repository.RepoPost;
import ru.skillbox.team13.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final RepoPost repoPost;
    private final PersonService personService;
    private final CommentService commentService;
    private final UserService userService;

    public Post getPostById(int id)  {
        return repoPost.findById(id).stream().findFirst().orElse(null);
    }

    public boolean deletePostById (int id) {
        try {
            repoPost.deleteById(id);
            return true;
        }
        catch (Exception ex)    {
            return false;
        }
    }
    public Post addPost(PostDTO postDTO) {
        Post post = new Post();
        PersonDTO author = userService.getCurrentUserDto();
        post.setAuthor(personService.getPersonBiId(author.getId()));
        post.setTime(LocalDateTime.now());
        fillPostFields(post, postDTO);
        repoPost.save(post);
        return post;
    }

    private void fillPostFields(Post post, PostDTO postDTO) {
        post.setPostText(postDTO.getPostText());
        if (postDTO.getComments() == null)  {
            post.setComments(null);
        }
        else {
            post.setComments(commentService.getSetComments(postDTO.getComments()));
        }
        post.setTitle(postDTO.getTitle());
        if (post.getTime() == null) {
            post.setTime(postDTO.getTime().toLocalDateTime());
        }
    }
}
