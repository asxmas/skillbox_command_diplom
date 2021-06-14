package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.mapper.PostMapper;
import ru.skillbox.team13.repository.RepoPost;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final RepoPost repoPost;

    public Post getPostById(int id)  {
        return repoPost.findById(id).stream().findFirst().orElse(null);
    }

    public boolean deletePostById (int id) {
        Post post = getPostById(id);
        if (post != null)   {
            post = null;
            return true;
        }
        return false;
    }
    public Post addPost(PostDTO postDTO) {
        Post post = PostMapper.convertPostDTOtoPost(postDTO);
        repoPost.save(post);
        return post;
    }
}
