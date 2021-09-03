package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotified is a Querydsl query type for Notified
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNotified extends EntityPathBase<Notified> {

    private static final long serialVersionUID = 1123322781L;

    public static final QNotified notified = new QNotified("notified");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QNotified(String variable) {
        super(Notified.class, forVariable(variable));
    }

    public QNotified(Path<? extends Notified> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotified(PathMetadata metadata) {
        super(Notified.class, metadata);
    }

}

