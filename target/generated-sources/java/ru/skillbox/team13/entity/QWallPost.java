package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWallPost is a Querydsl query type for WallPost
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWallPost extends EntityPathBase<WallPost> {

    private static final long serialVersionUID = -138314865L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWallPost wallPost = new QWallPost("wallPost");

    public final QPost _super;

    //inherited
    public final SetPath<Attachment, QAttachment> attachments;

    // inherited
    public final QPerson author;

    //inherited
    public final SetPath<Comment, QComment> comments;

    //inherited
    public final BooleanPath deleted;

    //inherited
    public final NumberPath<Integer> id;

    //inherited
    public final BooleanPath isBlocked;

    //inherited
    public final SetPath<Like, QLike> likes;

    //inherited
    public final StringPath postText;

    //inherited
    public final SetPath<Tag, QTag> tags;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> time;

    //inherited
    public final StringPath title;

    public final EnumPath<ru.skillbox.team13.entity.enums.WallPostType> type = createEnum("type", ru.skillbox.team13.entity.enums.WallPostType.class);

    public QWallPost(String variable) {
        this(WallPost.class, forVariable(variable), INITS);
    }

    public QWallPost(Path<? extends WallPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWallPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWallPost(PathMetadata metadata, PathInits inits) {
        this(WallPost.class, metadata, inits);
    }

    public QWallPost(Class<? extends WallPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPost(type, metadata, inits);
        this.attachments = _super.attachments;
        this.author = _super.author;
        this.comments = _super.comments;
        this.deleted = _super.deleted;
        this.id = _super.id;
        this.isBlocked = _super.isBlocked;
        this.likes = _super.likes;
        this.postText = _super.postText;
        this.tags = _super.tags;
        this.time = _super.time;
        this.title = _super.title;
    }

}

