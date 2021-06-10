package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.repository.RepoComment;

@Service
@RequiredArgsConstructor
public class CommentService {
    private RepoComment repoComment;

    public Comment getCommentById(Integer id)   {
        return repoComment.findById(id).stream().findFirst().orElse(null);
    }
}
