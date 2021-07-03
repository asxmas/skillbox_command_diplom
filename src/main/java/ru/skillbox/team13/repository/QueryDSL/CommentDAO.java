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

    public List<CommentDto> getComments(Integer... postIds) {
        QComment comment = QComment.comment;
        QPerson author = comment.author;
        QPost post = comment.post;
        QComment parentComment = comment.parent;

        Predicate where = post.id.in(postIds);
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
