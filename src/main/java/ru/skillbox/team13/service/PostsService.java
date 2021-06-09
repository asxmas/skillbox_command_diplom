package ru.skillbox.team13.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.PostDTO;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.repository.RepoPost;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final RepoPost repoPost;

    public Post getPostById(int id)  {
        return repoPost.findById(id).stream().findFirst().orElse(null);
    }

}
