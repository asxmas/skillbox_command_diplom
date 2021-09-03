package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotificationSetting is a Querydsl query type for NotificationSetting
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNotificationSetting extends EntityPathBase<NotificationSetting> {

    private static final long serialVersionUID = -990677056L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotificationSetting notificationSetting = new QNotificationSetting("notificationSetting");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QPerson person;

    public final EnumPath<ru.skillbox.team13.entity.enums.NotificationType> type = createEnum("type", ru.skillbox.team13.entity.enums.NotificationType.class);

    public final BooleanPath value = createBoolean("value");

    public QNotificationSetting(String variable) {
        this(NotificationSetting.class, forVariable(variable), INITS);
    }

    public QNotificationSetting(Path<? extends NotificationSetting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotificationSetting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotificationSetting(PathMetadata metadata, PathInits inits) {
        this(NotificationSetting.class, metadata, inits);
    }

    public QNotificationSetting(Class<? extends NotificationSetting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.person = inits.isInitialized("person") ? new QPerson(forProperty("person"), inits.get("person")) : null;
    }

}

