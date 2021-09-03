package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = -1692378782L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final QNotified _super = new QNotified(this);

    public final QPerson author;

    public final QDialog dialog;

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final StringPath messageText = createString("messageText");

    public final EnumPath<ru.skillbox.team13.entity.enums.MessageReadStatus> readStatus = createEnum("readStatus", ru.skillbox.team13.entity.enums.MessageReadStatus.class);

    public final QPerson recipient;

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new QPerson(forProperty("author"), inits.get("author")) : null;
        this.dialog = inits.isInitialized("dialog") ? new QDialog(forProperty("dialog"), inits.get("dialog")) : null;
        this.recipient = inits.isInitialized("recipient") ? new QPerson(forProperty("recipient"), inits.get("recipient")) : null;
    }

}

