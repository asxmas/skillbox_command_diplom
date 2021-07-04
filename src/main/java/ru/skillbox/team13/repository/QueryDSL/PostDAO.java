package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.QPost;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class PostDAO {

    private final EntityManagerFactory emf;

    @Deprecated
    public Page<Post> findByTextAndTime(String text, LocalDateTime earliest, LocalDateTime latest, Pageable p) {
        QPost post = QPost.post;
        EntityManager em = emf.createEntityManager();

        Predicate predicate = post.deleted.eq(false)
                .and(post.title.contains(text).or(post.postText.contains(text)));

        BooleanBuilder expr = new BooleanBuilder(predicate);
        if (earliest != null) expr.and(post.time.after(earliest));
        if (latest != null) expr.and(post.time.before(latest));

        JPAQuery<Post> query = new JPAQuery<>(em);
        QueryResults<Post> results = query
                .from(post)
                .innerJoin(post.author).fetchJoin()
                .where(expr)
                .offset(p.getOffset())
                .limit(p.getPageSize())
                .fetchResults();
        em.close();
        return new PageImpl<>(results.getResults(), p, results.getTotal());
    }

    public Page<PostDto> getPostDtosByAuthorIdAndSubstring(List<Integer> authorIds, String substr, Pageable p) {
        QPost post = QPost.post;
        Predicate where = post.author.id.in(authorIds).and(post.deleted.isFalse());
        BooleanBuilder whereBool = new BooleanBuilder(where);
        if (nonNull(substr) && !substr.isBlank()) {
            whereBool.and(post.title.containsIgnoreCase(substr)
                    .or(post.postText.containsIgnoreCase(substr)));
        }
        return executeDtoQuery(whereBool, p);
    }

    public Page<PostDto> getPostsDtosByTimeAndSubstring(String text, LocalDateTime earliest, LocalDateTime latest,
                                                        Pageable p) {
        QPost post = QPost.post;

        Predicate predicate = post.deleted.isFalse();

        BooleanBuilder expr = new BooleanBuilder(predicate);
        if (nonNull(earliest)) expr.and(post.time.after(earliest));
        if (nonNull(latest)) expr.and(post.time.before(latest));
        if (nonNull(text) && !text.isBlank()) {
            expr.and(post.title.containsIgnoreCase(text).or(post.postText.containsIgnoreCase(text)));
        }
        return executeDtoQuery(expr, p);
    }

    private Page<PostDto> executeDtoQuery(Predicate where, Pageable p) {
        EntityManager em = emf.createEntityManager();
        QPost post = QPost.post;
        JPAQuery<PostDto> query = new JPAQuery<>(em);
        QueryResults<PostDto> results = query.select(Projections.constructor(PostDto.class,
                post.id, post.time, post.author.id, post.title, post.postText, post.isBlocked, post.likes.size()))
                .from(post)
                .where(where)
                .offset(p.getOffset())
                .limit(p.getPageSize())
                .orderBy(post.time.desc())
                .fetchResults();
        em.close();
        return new PageImpl<>(results.getResults(), p, results.getTotal());
    }
}
