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

    public static final QStore store = new QStore("store");

    public final QBaseEntity _super = new QBaseEntity(this);

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
        super(Store.class, forVariable(variable));
    }

    public QStore(Path<? extends Store> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStore(PathMetadata metadata) {
        super(Store.class, metadata);
    }

}

