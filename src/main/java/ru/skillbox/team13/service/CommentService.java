package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.CommentDTO;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.repository.RepoComment;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentService {
    private RepoComment repoComment;

    public Comment getCommentById(Integer id)   {
        return repoComment.findById(id).stream().findFirst().orElse(null);
    }

    public Set<Comment> getSetComments(Set<CommentDTO> commentDTOSet)   {
        Set<Comment> comments = new HashSet<>();
        for (CommentDTO commentDTO : commentDTOSet)  {
            comments.add(getCommentById(commentDTO.getId()));
        }
        return comments;
    }
}
