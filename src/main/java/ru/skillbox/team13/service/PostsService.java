package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.User;
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
    public Post addPost(PostDto postDTO, Person currentPerson) {
        Post post = new Post();
        post.setAuthor(currentPerson);
        post.setTime(LocalDateTime.now());
        fillPostFields(post, postDTO);
        repoPost.save(post);
        return post;
    }

    private void fillPostFields(Post post, PostDto postDTO) {
        post.setPostText(postDTO.getText());
        if (postDTO.getComments() == null)  {
            post.setComments(null);
        }
        post.setTitle(postDTO.getTitle());
    }

    public Set<PostDto> getSetPostsByAuthorId(Integer id)   {
        List<Post> postsByAuthor = repoPost.getPostsByAuthorId(PageRequest.of(0,10),id);
        return PostMapper.convertSetPostToSetPostDTO(Set.copyOf(postsByAuthor));
    }

    @Modifying
    public void setInactiveAuthor() {
        User currentUser = userService.getAuthorizedUser();
        Person currentPerson = currentUser.getPerson();
        Person inactiveAuthor = userService.getInactivePerson();
        List<Post> posts = repoPost.getPostsByAuthorId(PageRequest.of(0,10), currentPerson.getId());
        for (Post post : posts) {
            post.setAuthor(inactiveAuthor);
        }
        repoPost.saveAllAndFlush(posts);
    }
}
