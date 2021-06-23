package ru.skillbox.team13.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.LikeCount;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Integer countAllByAuthorIn(List<Person> authors);

    @Query("select p from Post p join fetch p.author where p.author in :authors order by p.time desc ")
    List<Post> findPostsAndAuthorsFromAuthors(Pageable p, List<Person> authors);

    @Query("select p.likes.size as likeCount, p.id as id from Post p where p in :posts")
    List<LikeCount> countLikesByPosts(List<Post> posts);
}
