package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Like;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.projection.Liker;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query("select l.person.id as likerId from Like l where l.post.id = :itemId or l.comment.id = :itemId")
    List<Liker> findLikersProjectionsForItemId(int itemId);

    @Query("select count(l) from Like l  where l.person = :person and (l.post.id = :itemId or l.comment.id = :itemId)")
    Integer countByLikerAndItemId(Person person, int itemId);

    @Modifying
    @Query("delete from Like l where l.person = :person and (l.post.id = :itemId or l.comment.id = :itemId)")
    void deleteByLikerAndId(Person person, int itemId);
}
