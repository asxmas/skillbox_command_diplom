package ru.skillbox.team13.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @NotNull
    @Override
    @Query("from Post p where p.deleted = false and p.id = :id")
    Optional<Post> findById(@NotNull Integer id);

    @Override
    @Query("select case when count(p)> 0 then true else false end from Post p where p.deleted = false and p.id = :id")
    boolean existsById(@NotNull Integer id);
}
