package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.CommentDto;
import ru.skillbox.team13.entity.Comment;
import ru.skillbox.team13.entity.QComment;
import ru.skillbox.team13.entity.QPerson;
import ru.skillbox.team13.entity.QPost;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@PersistenceContext
@RequiredArgsConstructor
public class CommentDAO {

    private final EntityManager em;

    public List<CommentDto> getCommentDtosForPostIds(Integer postId) {
        QComment comment = QComment.comment;
        QPost post = comment.post;
        Predicate where = post.id.eq(postId).and(comment.deleted.isFalse());
        return find(where);
    }

    public List<CommentDto> getCommentDtosForPostIds(List<Integer> postIds) {
        QComment comment = QComment.comment;
        QPost post = comment.post;
        Predicate where = post.id.in(postIds).and(comment.deleted.isFalse());
        return find(where);
    }

    public Page<CommentDto> getCommentDtosForPostIds(int postId, Pageable pageable) {
        QComment comment = QComment.comment;
        QPost post = comment.post;
        QComment parentComment = comment.parent;
        QPerson author = comment.author;

        QueryResults<CommentDto> qr = new JPAQuery<>(em)
                .select(Projections.constructor(CommentDto.class, comment.id, post.id, parentComment.id,
                        comment.commentText, comment.time, author.id, comment.isBlocked))
                .from(comment)
                .where(post.id.eq(postId).and(comment.deleted.isFalse()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(qr.getResults(), pageable, qr.getTotal());
    }

    public CommentDto getCommentDtoForId(int commentId) {
        QComment comment = QComment.comment;
        Predicate where = comment.id.eq(commentId).and(comment.deleted.isFalse());
        return find(where).get(0);
    }

    private List<CommentDto> find(Predicate where) {
        QComment comment = QComment.comment;
        QPerson author = comment.author;
        QPost post = comment.post;
        QComment parentComment = comment.parent;
        JPAQuery<Comment> query = new JPAQuery<>(em);

        return query.select(Projections.constructor(CommentDto.class,
                comment.id, post.id, parentComment.id, comment.commentText, comment.time, author.id, comment.isBlocked))
                .from(comment)
                .where(where)
                .fetch();
    }
}
