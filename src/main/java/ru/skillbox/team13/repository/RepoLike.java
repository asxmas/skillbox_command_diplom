package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.repository.projection.Liker;

import java.util.List;
import java.util.Optional;

public interface RepoLike extends CrudRepository<Like, Integer> {
    @Query("select l from Like l where l.postOrComment.id = :id and l.person.id = :personId")
    Optional<Like> getLikeForPostOrCommentById(int id, int personId);

    @Query("select l.person.id as likerId from Like l where l.postOrComment.id = :itemId")
    List<Liker> findLikersProjectionsForItemId(int itemId);
}
