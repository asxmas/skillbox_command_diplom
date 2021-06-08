package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Post;

public interface RepoPost extends CrudRepository<Post, Integer> {
}
