package ru.skillbox.team13.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @NotNull
    @Override
    @Query("from Post p where p.deleted = false and p.id = :id")
    Optional<Post> findById(@NotNull Integer id);

    @Override
    @Query("select case when count(p)> 0 then true else false end from Post p where p.deleted = false and p.id = :id")
    boolean existsById(@NotNull Integer id);

    @Modifying
    Set<Post> findAllByAuthor(Person author);

    //todo Сделать поиск по author_id
    @Query("select p from Post p where p.author.id = :id ")
    List<Post> getPostsByAuthorId(Pageable p, @Param("id") Integer id);
}
