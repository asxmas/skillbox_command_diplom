package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.QPost;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDAO {

    private final EntityManagerFactory emf;

    public List<Post> findByTextAndTime(String text, LocalDateTime earliest, LocalDateTime latest) {
        QPost post = QPost.post;
        EntityManager em = emf.createEntityManager();
        JPAQuery<Post> query = new JPAQuery<>(em);

        Predicate pred = post.deleted.eq(false).and(post.title.contains(text).or(post.postText.contains(text)));

        BooleanBuilder where = new BooleanBuilder(pred);
        if (earliest != null) where.and(post.time.after(earliest));
        if (latest != null) where.and(post.time.before(latest));

        return query.from(post).where(where).fetch();
    }
}
