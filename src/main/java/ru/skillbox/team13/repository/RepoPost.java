package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Post;

public interface RepoPost extends JpaRepository<Post, Integer> {
}
