package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.QPost;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class PostDAO {

    private final EntityManagerFactory emf;

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
}
