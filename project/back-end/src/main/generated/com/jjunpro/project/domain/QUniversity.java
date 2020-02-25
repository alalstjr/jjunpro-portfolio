package com.jjunpro.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUniversity is a Querydsl query type for University
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUniversity extends EntityPathBase<University> {

    private static final long serialVersionUID = 668076060L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUniversity university = new QUniversity("university");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAccount account;

    public final SetPath<Comment, QComment> comments = this.<Comment, QComment>createSet("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    //inherited
    public final BooleanPath controlStatus = _super.controlStatus;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<File, QFile> files = this.<File, QFile>createList("files", File.class, QFile.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath ip = createString("ip");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final BooleanPath publicStatus = _super.publicStatus;

    public final NumberPath<Byte> uniAtmosphere = createNumber("uniAtmosphere", Byte.class);

    public final StringPath uniContent = createString("uniContent");

    public final SetPath<Account, QAccount> uniLike = this.<Account, QAccount>createSet("uniLike", Account.class, QAccount.class, PathInits.DIRECT2);

    public final StringPath uniName = createString("uniName");

    public final NumberPath<Byte> uniPrice = createNumber("uniPrice", Byte.class);

    public final NumberPath<Integer> uniStar = createNumber("uniStar", Integer.class);

    public final StringPath uniSubject = createString("uniSubject");

    public final StringPath uniTag = createString("uniTag");

    public QUniversity(String variable) {
        this(University.class, forVariable(variable), INITS);
    }

    public QUniversity(Path<? extends University> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUniversity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUniversity(PathMetadata metadata, PathInits inits) {
        this(University.class, metadata, inits);
    }

    public QUniversity(Class<? extends University> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account"), inits.get("account")) : null;
    }

}

