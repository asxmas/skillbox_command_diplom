package ru.skillbox.team13.repository.QueryDSL;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.skillbox.team13.dto.PostDto;
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.exception.BadRequestException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
@PersistenceContext
@RequiredArgsConstructor
public class PostDAO {

    private final EntityManager em;

    public Page<PostDto> getPostDTOs(int viewerId, List<Integer> authorIds, String substr, boolean withFuturePosts,
                                     Pageable pageable) {
        QPost post = QPost.post;
        QPerson author = QPerson.person;

        Predicate where = author.id.in(authorIds).and(post.deleted.isFalse());
        BooleanBuilder whereBool = new BooleanBuilder(where);
        if (nonNull(substr) && !substr.isBlank()) {
            whereBool.and(post.title.containsIgnoreCase(substr)
                    .or(post.postText.containsIgnoreCase(substr)));
        }
        if (!withFuturePosts) {
            whereBool.and(post.time.before(LocalDateTime.now()));
        }

        QueryResults<PostDto> results = doPageQuery(viewerId, whereBool, pageable.getOffset(), pageable.getPageSize());
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public Page<PostDto> getPostDTOs(int viewerId, String text, LocalDateTime earliest, LocalDateTime latest,
                                     String authorName, String[] tagNames, Pageable pageable) {
        QPost post = QPost.post;

        Predicate where = post.deleted.isFalse();
        BooleanBuilder whereBool = new BooleanBuilder(where);
        if (nonNull(earliest)) whereBool.and(post.time.after(earliest));
        if (nonNull(latest)) whereBool.and(post.time.before(latest));
        if (nonNull(authorName)) whereBool.and(post.author.firstName.containsIgnoreCase(authorName)
                .or(post.author.lastName.containsIgnoreCase(authorName)));
        if (nonNull(tagNames) && tagNames.length > 0) {
            for (String tagName : tagNames) {
                whereBool.and(post.tags.any().tag.eq(tagName));
            }
        }
        if (nonNull(text) && !text.isBlank()) {
            whereBool.and(
                    post.title.containsIgnoreCase(text)
                            .or(post.postText.containsIgnoreCase(text))
                            .or(post.title.contains(text))
                            .or(post.postText.contains(text))); //todo test on heroku collation
        }

        QueryResults<PostDto> results = doPageQuery(viewerId, whereBool, pageable.getOffset(), pageable.getPageSize());
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    public PostDto getPostDTO(int viewerId, int postId) {
        QPost post = QPost.post;
        QPerson author = QPerson.person;
        QLike like = QLike.like;

        Predicate where = post.id.eq(postId).and(post.deleted.isFalse());
        JPAQuery<PostDto> query = new JPAQuery<>(em);
        PostDto dto = query.select(getSelectExpr(viewerId))
                .distinct()
                .from(post)
                .leftJoin(post.likes, like)
                .join(post.author, author)
                .where(where).fetchFirst();
        if (Objects.isNull(dto)) throw new BadRequestException("Post request '" + where + "' returns 0 results.");
        return dto;
    }

    public void edit(int id, LocalDateTime time, String title, String text, Set<Tag> tags) {
        Post post = em.find(Post.class, id);
        if (isNull(post) || post.isDeleted()) {
            throw new BadRequestException("Post id=" + id + "does not exist or was deleted.");
        }

        if (nonNull(time)) {
            post.setTime(time);
        }
        post.setTitle(title);
        post.setPostText(text);
        post.setTags(tags);
    }

    public void delete(int id, boolean delete) {
        Post post = em.find(Post.class, id);
        if (isNull(post) || post.isDeleted() == delete) {
            throw new BadRequestException("Post id=" + id + "does not exist or was deleted.");
        }
        post.setDeleted(delete);
    }

    private QueryResults<PostDto> doPageQuery(int viewerId, Predicate where, long offset, int limit) {
        QPost post = QPost.post;
        QPerson author = QPerson.person;
        QLike like = QLike.like;

        JPAQuery<PostDto> query = new JPAQuery<>(em);
        return query.select(getSelectExpr(viewerId))
                .distinct()  //todo something produces duplicates
                .from(post)
                .leftJoin(post.likes, like)
                .join(post.author, author)
                .where(where)
                .offset(offset)
                .limit(limit)
                .orderBy(post.time.desc())
                .fetchResults();
    }

    private Expression<PostDto> getSelectExpr(int viewerId) {
        QPost post = QPost.post;
        QPerson author = post.author;

        return Projections.constructor(PostDto.class,
                post.id, post.time, author.id, author.firstName, author.lastName, author.photo, author.lastOnlineTime,
                post.title, post.postText, post.isBlocked, post.likes.size(),
                QLike.like.person.id
                        .when(viewerId).then(true)
                        .otherwise(false),
                new CaseBuilder()
                        .when(post.time.before(LocalDateTime.now())).then("POSTED")
                        .otherwise("QUEUED"));
    }
}
