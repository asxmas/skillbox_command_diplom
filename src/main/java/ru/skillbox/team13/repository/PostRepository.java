package ru.skillbox.team13.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.LikeCount;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Integer countAllByAuthorIn(List<Person> authors);

    Integer countAllByAuthorInAndTitleLikeOrPostTextLike(List<Person> authors, String q1, String q2);

    @Query("select p from Post p join fetch p.author where p.author in :authors and p.deleted = false order by p.time desc ")
    List<Post> findPostsAndAuthorsFromAuthors(Pageable p, List<Person> authors);

    @Query("select p from Post p join fetch p.author where p.author in :authors and " +
            "(lower(p.title) like :query or lower(p.postText) like :query) and p.deleted = false order by p.time desc ")
    List<Post> findPostsAndAuthorsFromAuthors(Pageable p, List<Person> authors, String query);

    @Query("select size(p.likes) as likeCount, p.id as id from Post p where p in :posts")
    List<LikeCount> countLikesByPosts(List<Post> posts);

    @Query("select size(p.likes) as likeCount, p.id as id from Post p where p = :post")
    LikeCount countLikesByPosts(Post post);

    @NotNull
    @Override
    @Query("from Post p where p.deleted = false and p.id = :id")
    Optional<Post> findById(@NotNull Integer id);

    Optional<Post> findByIdAndDeleted(Integer id, Boolean deleted);

    @Override
    @Query("select case when count(p)> 0 then true else false end from Post p where p.deleted = false and p.id = :id")
    boolean existsById(@NotNull Integer id);
}
