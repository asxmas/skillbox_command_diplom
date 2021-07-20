package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
