package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotification is a Querydsl query type for Notification
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNotification extends EntityPathBase<Notification> {

    private static final long serialVersionUID = 1425920112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotification notification = new QNotification("notification");

    public final QPerson author;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath info = createString("info");

    public final BooleanPath read = createBoolean("read");

    public final QPerson receiver;

    public final DateTimePath<java.time.LocalDateTime> sentTime = createDateTime("sentTime", java.time.LocalDateTime.class);

    public final QNotified source;

    public final EnumPath<ru.skillbox.team13.entity.enums.NotificationType> type = createEnum("type", ru.skillbox.team13.entity.enums.NotificationType.class);

    public QNotification(String variable) {
        this(Notification.class, forVariable(variable), INITS);
    }

    public QNotification(Path<? extends Notification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotification(PathMetadata metadata, PathInits inits) {
        this(Notification.class, metadata, inits);
    }

    public QNotification(Class<? extends Notification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new QPerson(forProperty("author"), inits.get("author")) : null;
        this.receiver = inits.isInitialized("receiver") ? new QPerson(forProperty("receiver"), inits.get("receiver")) : null;
        this.source = inits.isInitialized("source") ? new QNotified(forProperty("source")) : null;
    }

}

