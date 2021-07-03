package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.QComment;
import ru.skillbox.team13.entity.QPerson;
import ru.skillbox.team13.entity.QPost;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDAO {

    private final EntityManagerFactory emf;
    private QComment comment = QComment.comment;
    private QPerson author = comment.author;
    private QPost post = comment.post;
    private QComment parentComment = comment.parent;

    public List<CommentDto> getComments(Integer postId) {
        Predicate where = post.id.eq(postId);
        return find(where);
    }

    public List<CommentDto> getComments(List<Integer> postIds) {
        Predicate where = post.id.in(postIds);
        return find(where);
    }

    private List<CommentDto> find(Predicate where) {
        EntityManager em = emf.createEntityManager();
        JPAQuery<Comment> query = new JPAQuery<>(em);

        List<CommentDto> list = query.select(Projections.constructor(CommentDto.class,
                comment.id,
                post.id,
                parentComment.id,
                comment.commentText,
                comment.time,
                author.id,
                comment.isBlocked))
                .from(comment)
                .where(where)
                .fetch();
        em.close();
        return list;
    }
}
