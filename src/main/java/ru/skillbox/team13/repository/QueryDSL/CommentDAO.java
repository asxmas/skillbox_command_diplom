package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.QComment;
import ru.skillbox.team13.entity.QLike;
import ru.skillbox.team13.entity.QPerson;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@PersistenceContext
@RequiredArgsConstructor
public class CommentDAO {

    private final EntityManager em;

    public List<CommentDto> getCommentDTOs(int viewerId, List<Integer> postIds) {
        QComment comment = QComment.comment;
        Predicate where = comment.post.id.in(postIds).and(comment.deleted.isFalse());

        return new JPAQuery<>(em)
                .select(getSelectExpression(viewerId))
                .from(comment)
                .leftJoin(comment.likes, QLike.like)
                .where(where).fetch();
    }

    public List<CommentDto> getCommentDTO(int viewerId, int commentId) {
        QComment comment = QComment.comment;

        Predicate where = comment.deleted.isFalse().and(comment.id.eq(commentId).or(comment.parent.id.eq(commentId)));

        //returns comment and all children comments
        return new JPAQuery<>(em)
                .select(getSelectExpression(viewerId))
                .from(comment)
                .leftJoin(comment.likes, QLike.like)
                .where(where).fetch();
    }

    public Page<CommentDto> getCommentDTOs(int viewerId, int postId, Pageable pageable) {
        QComment comment = QComment.comment;

        Predicate where = comment.post.id.eq(postId).and(comment.deleted.isFalse());

        QueryResults<CommentDto> qr = new JPAQuery<>(em)
                .select(getSelectExpression(viewerId))
                .from(comment)
                .leftJoin(comment.likes, QLike.like)
                .where(where)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(qr.getResults(), pageable, qr.getTotal());
    }

    private Expression<CommentDto> getSelectExpression(int viewerId) {
        QComment comment = QComment.comment;
        QPerson author = comment.author;

        return Projections.constructor(CommentDto.class,
                comment.id, comment.commentText, comment.post.id, comment.parent.id, comment.time,
                author.id, author.firstName, author.lastName, author.photo, author.lastOnlineTime, //PersonCompactDto
                comment.isBlocked,
                QLike.like.person.id                                                   //liked by person with viewerId
                        .when(viewerId).then(true)
                        .otherwise(false));
    }
}
