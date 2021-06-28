package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.team13.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface RepoPost extends JpaRepository<Post, Integer> {
    //todo Сделать поиск по author_id
    @Query("select p from Post p where p.author.id = :id ")
    List<Post> getPostsByAuthorId(Pageable p, @Param("id") Integer id);
}
