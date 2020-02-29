package com.jjunpro.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 1458379411L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAccount account;

    //inherited
    public final BooleanPath controlStatus = _super.controlStatus;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final SetPath<FoodMenu, QFoodMenu> foodMenu = this.<FoodMenu, QFoodMenu>createSet("foodMenu", FoodMenu.class, QFoodMenu.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final BooleanPath publicStatus = _super.publicStatus;

    public final StringPath stoAddress = createString("stoAddress");

    public final StringPath stoId = createString("stoId");

    public final StringPath stoName = createString("stoName");

    public final SetPath<University, QUniversity> stoUniList = this.<University, QUniversity>createSet("stoUniList", University.class, QUniversity.class, PathInits.DIRECT2);

    public final StringPath stoUrl = createString("stoUrl");

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account"), inits.get("account")) : null;
    }

}

