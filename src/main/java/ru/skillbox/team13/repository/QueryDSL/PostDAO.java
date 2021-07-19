package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.Post;
import ru.skillbox.team13.entity.QLike;
import ru.skillbox.team13.entity.QPerson;
import ru.skillbox.team13.entity.QPost;
import ru.skillbox.team13.exception.BadRequestException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
@PersistenceContext
@RequiredArgsConstructor
public class PostDAO {

    private final EntityManager em;

    //todo tags
    public Page<PostDto> getPostDTOs(int viewerId, List<Integer> authorIds, String substr, Pageable pageable) {
        QPost post = QPost.post;
        QPerson author = QPerson.person;
        QLike like = QLike.like;

        Predicate where = author.id.in(authorIds).and(post.deleted.isFalse());
        BooleanBuilder whereBool = new BooleanBuilder(where);
        if (nonNull(substr) && !substr.isBlank()) {
            whereBool.and(post.title.containsIgnoreCase(substr)
                    .or(post.postText.containsIgnoreCase(substr)));
        }

        JPAQuery<PostDto> query = new JPAQuery<>(em);
        QueryResults<PostDto> results = query.select(Projections.constructor(PostDto.class,
                post.id, post.time, author.id, author.firstName, author.lastName, author.photo, author.lastOnlineTime,
                post.title, post.postText, post.isBlocked, post.likes.size(),
                like.person.id                                          //liked by this person
                        .when(viewerId).then(true)
                        .otherwise(false)))
                .distinct()  //todo something produces duplicates
                .from(post)
                .leftJoin(post.likes, like)
                .join(post.author, author)
                .where(whereBool)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.time.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Page<PostDto> getPostDTOs(int viewerId, String text, LocalDateTime earliest, LocalDateTime latest, Pageable pageable) {
        QPost post = QPost.post;
        QPerson author = QPerson.person;
        QLike like = QLike.like;


        Predicate where = post.deleted.isFalse();
        BooleanBuilder whereBool = new BooleanBuilder(where);
        if (nonNull(earliest)) whereBool.and(post.time.after(earliest));
        if (nonNull(latest)) whereBool.and(post.time.before(latest));
        if (nonNull(text) && !text.isBlank()) {
            whereBool.and(post.title.containsIgnoreCase(text).or(post.postText.containsIgnoreCase(text)));
        }
        JPAQuery<PostDto> query = new JPAQuery<>(em);
        QueryResults<PostDto> results = query.select(Projections.constructor(PostDto.class,
                post.id, post.time, author.id, author.firstName, author.lastName, author.photo, author.lastOnlineTime,
                post.title, post.postText, post.isBlocked, post.likes.size(),
                like.person.id                                          //liked by this person
                        .when(viewerId).then(true)
                        .otherwise(false)))
                .distinct()  //todo something produces duplicates
                .from(post)
                .leftJoin(post.likes, like)
                .join(post.author, author)
                .where(whereBool)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.time.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public PostDto getPostDTO(int viewerId, int postId) {
        QPost post = QPost.post;
        QPerson author = QPerson.person;
        QLike like = QLike.like;

        Predicate where = post.id.eq(postId).and(post.deleted.isFalse());
        JPAQuery<PostDto> query = new JPAQuery<>(em);
        PostDto dto = query.select(Projections.constructor(PostDto.class,
                post.id, post.time, author.id, author.firstName, author.lastName, author.photo, author.lastOnlineTime,
                post.title, post.postText, post.isBlocked, post.likes.size(),
                like.person.id                                          //liked by this person
                        .when(viewerId).then(true)
                        .otherwise(false)))
                .distinct()  //todo something produces duplicates
                .from(post)
                .leftJoin(post.likes, like)
                .join(post.author, author)
                .where(where).fetchFirst();
        if (Objects.isNull(dto)) throw new BadRequestException("Post request '" + where + "' returns 0 results.");
        return dto;
    }

    @Deprecated
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

    @Deprecated
    public Page<PostDto> getPostsDtosByTimeAndSubstring(String text, LocalDateTime earliest, LocalDateTime latest, Pageable p) {
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

    @Deprecated
    public PostDto getSingleDtoById(int id) {
        Predicate where = QPost.post.id.eq(id).and(QPost.post.deleted.isFalse());
        Page<PostDto> page = executeDtoQuery(where, PageRequest.of(0, 1));
        if (page.getTotalElements() == 0)
            throw new BadRequestException("Post request '" + where + "' returns 0 results.");
        return page.getContent().get(0);
    }

    @Deprecated
    private Page<PostDto> executeDtoQuery(Predicate where, Pageable p) {
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
        return new PageImpl<>(results.getResults(), p, results.getTotal());
    }

    public void edit(int id, LocalDateTime time, String title, String text) {
        Post post = em.find(Post.class, id);
        if (isNull(post) || post.isDeleted()) {
            throw new BadRequestException("Post id=" + id + "does not exist or was deleted.");
        }

        if (nonNull(time)) {
            post.setTime(time);
        }
        post.setTitle(title);
        post.setPostText(text);
    }

    public void delete(int id, boolean delete) {
        Post post = em.find(Post.class, id);
        if (isNull(post) || post.isDeleted() == delete) {
            throw new BadRequestException("Post id=" + id + "does not exist or was deleted.");
        }
        post.setDeleted(delete);
    }
}
