package ru.skillbox.team13.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerson is a Querydsl query type for Person
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPerson extends EntityPathBase<Person> {

    private static final long serialVersionUID = -1908397414L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerson person = new QPerson("person");

    public final QNotified _super = new QNotified(this);

    public final StringPath about = createString("about");

    public final DateTimePath<java.time.LocalDateTime> birthDate = createDateTime("birthDate", java.time.LocalDateTime.class);

    public final SetPath<Block, QBlock> blocks = this.<Block, QBlock>createSet("blocks", Block.class, QBlock.class, PathInits.DIRECT2);

    public final QCity city;

    public final SetPath<Comment, QComment> comments = this.<Comment, QComment>createSet("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final QCountry country;

    public final BooleanPath deleted = createBoolean("deleted");

    public final SetPath<Dialog, QDialog> dialogs = this.<Dialog, QDialog>createSet("dialogs", Dialog.class, QDialog.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final BooleanPath isBlocked = createBoolean("isBlocked");

    public final StringPath lastName = createString("lastName");

    public final DateTimePath<java.time.LocalDateTime> lastOnlineTime = createDateTime("lastOnlineTime", java.time.LocalDateTime.class);

    public final SetPath<Like, QLike> likes = this.<Like, QLike>createSet("likes", Like.class, QLike.class, PathInits.DIRECT2);

    public final EnumPath<ru.skillbox.team13.entity.enums.PersonMessagePermission> messagesPermission = createEnum("messagesPermission", ru.skillbox.team13.entity.enums.PersonMessagePermission.class);

    public final SetPath<Message, QMessage> messagesReceived = this.<Message, QMessage>createSet("messagesReceived", Message.class, QMessage.class, PathInits.DIRECT2);

    public final SetPath<Message, QMessage> messagesSent = this.<Message, QMessage>createSet("messagesSent", Message.class, QMessage.class, PathInits.DIRECT2);

    public final StringPath phone = createString("phone");

    public final StringPath photo = createString("photo");

    public final SetPath<Post, QPost> posts = this.<Post, QPost>createSet("posts", Post.class, QPost.class, PathInits.DIRECT2);

    public final SetPath<Friendship, QFriendship> receivedFriendships = this.<Friendship, QFriendship>createSet("receivedFriendships", Friendship.class, QFriendship.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final SetPath<Friendship, QFriendship> requestedFriendships = this.<Friendship, QFriendship>createSet("requestedFriendships", Friendship.class, QFriendship.class, PathInits.DIRECT2);

    public QPerson(String variable) {
        this(Person.class, forVariable(variable), INITS);
    }

    public QPerson(Path<? extends Person> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPerson(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPerson(PathMetadata metadata, PathInits inits) {
        this(Person.class, metadata, inits);
    }

    public QPerson(Class<? extends Person> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.city = inits.isInitialized("city") ? new QCity(forProperty("city")) : null;
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country")) : null;
    }

}

