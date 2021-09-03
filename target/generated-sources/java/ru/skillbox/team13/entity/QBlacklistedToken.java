package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBlacklistedToken is a Querydsl query type for BlacklistedToken
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBlacklistedToken extends EntityPathBase<BlacklistedToken> {

    private static final long serialVersionUID = 820351458L;

    public static final QBlacklistedToken blacklistedToken = new QBlacklistedToken("blacklistedToken");

    public final DateTimePath<java.time.LocalDateTime> expiredDate = createDateTime("expiredDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath token = createString("token");

    public QBlacklistedToken(String variable) {
        super(BlacklistedToken.class, forVariable(variable));
    }

    public QBlacklistedToken(Path<? extends BlacklistedToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBlacklistedToken(PathMetadata metadata) {
        super(BlacklistedToken.class, metadata);
    }

}

