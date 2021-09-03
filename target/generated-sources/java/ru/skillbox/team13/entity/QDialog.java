package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDialog is a Querydsl query type for Dialog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDialog extends EntityPathBase<Dialog> {

    private static final long serialVersionUID = 2046200973L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDialog dialog = new QDialog("dialog");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath inviteLink = createString("inviteLink");

    public final QMessage lastMessage;

    public final ListPath<Message, QMessage> messages = this.<Message, QMessage>createList("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    public final SetPath<Person, QPerson> persons = this.<Person, QPerson>createSet("persons", Person.class, QPerson.class, PathInits.DIRECT2);

    public QDialog(String variable) {
        this(Dialog.class, forVariable(variable), INITS);
    }

    public QDialog(Path<? extends Dialog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDialog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDialog(PathMetadata metadata, PathInits inits) {
        this(Dialog.class, metadata, inits);
    }

    public QDialog(Class<? extends Dialog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lastMessage = inits.isInitialized("lastMessage") ? new QMessage(forProperty("lastMessage"), inits.get("lastMessage")) : null;
    }

}

