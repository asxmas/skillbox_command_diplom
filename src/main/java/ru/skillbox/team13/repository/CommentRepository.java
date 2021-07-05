package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.projection.CommentProjection;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByPost(Post post);


    @Query("select c.id as id, c.commentText as text, c.post.id as postId," +
            "c.parent.id as parentId, c.time as time, c.author.id as authorId, " +
            "c.isBlocked as blocked from Comment c")
    List<CommentProjection> getCommProjections(Post post);

}
