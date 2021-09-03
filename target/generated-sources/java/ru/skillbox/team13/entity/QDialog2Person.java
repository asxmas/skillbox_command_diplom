package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDialog2Person is a Querydsl query type for Dialog2Person
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDialog2Person extends EntityPathBase<Dialog2Person> {

    private static final long serialVersionUID = -1004944134L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDialog2Person dialog2Person = new QDialog2Person("dialog2Person");

    public final QDialog dialog;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QPerson person;

    public final NumberPath<Integer> unreadCount = createNumber("unreadCount", Integer.class);

    public QDialog2Person(String variable) {
        this(Dialog2Person.class, forVariable(variable), INITS);
    }

    public QDialog2Person(Path<? extends Dialog2Person> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDialog2Person(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDialog2Person(PathMetadata metadata, PathInits inits) {
        this(Dialog2Person.class, metadata, inits);
    }

    public QDialog2Person(Class<? extends Dialog2Person> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dialog = inits.isInitialized("dialog") ? new QDialog(forProperty("dialog"), inits.get("dialog")) : null;
        this.person = inits.isInitialized("person") ? new QPerson(forProperty("person"), inits.get("person")) : null;
    }

}

