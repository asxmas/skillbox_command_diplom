package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Comment;

public interface RepoComment extends CrudRepository<Comment, Integer> {
}
