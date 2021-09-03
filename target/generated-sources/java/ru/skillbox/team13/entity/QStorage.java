package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStorage is a Querydsl query type for Storage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStorage extends EntityPathBase<Storage> {

    private static final long serialVersionUID = -236610602L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStorage storage = new QStorage("storage");

    public final NumberPath<Long> bytes = createNumber("bytes", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath fileFormat = createString("fileFormat");

    public final StringPath fileName = createString("fileName");

    public final StringPath fileType = createString("fileType");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> ownerId = createNumber("ownerId", Integer.class);

    public final QPerson person;

    public final StringPath rawFileURL = createString("rawFileURL");

    public final StringPath relativeFilePath = createString("relativeFilePath");

    public QStorage(String variable) {
        this(Storage.class, forVariable(variable), INITS);
    }

    public QStorage(Path<? extends Storage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStorage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStorage(PathMetadata metadata, PathInits inits) {
        this(Storage.class, metadata, inits);
    }

    public QStorage(Class<? extends Storage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.person = inits.isInitialized("person") ? new QPerson(forProperty("person"), inits.get("person")) : null;
    }

}

