package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.mapper.PostMapper;
import ru.skillbox.team13.repository.RepoPost;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final RepoPost repoPost;
    private final CommentService commentService;

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
    public Post addPost(PostDTO postDTO, Person currentPerson) {
        Post post = new Post();
        post.setAuthor(currentPerson);
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
            post.setTime(postDTO.getTime());
        }
    }

    public Set<PostDTO> getSetPostsByAuthorId(Integer id)   {
        List<Post> postsByAuthor = repoPost.getPostsByAuthorId(PageRequest.of(0,10),id);
        return PostMapper.convertSetPostToSetPostDTO(Set.copyOf(postsByAuthor));
    }

    @Modifying
    public void setNullAuthor(Integer personId) {
        List<Post> posts = repoPost.getPostsByAuthorId(PageRequest.of(0,10), personId);
        for (Post post : posts) {
            post.setAuthor(null);
        }
        repoPost.saveAllAndFlush(posts);
    }
}
