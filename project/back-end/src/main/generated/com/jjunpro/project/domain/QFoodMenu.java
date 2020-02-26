package com.jjunpro.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodMenu is a Querydsl query type for FoodMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFoodMenu extends EntityPathBase<FoodMenu> {

    private static final long serialVersionUID = 1649041387L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFoodMenu foodMenu = new QFoodMenu("foodMenu");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final BooleanPath controlStatus = _super.controlStatus;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final QFile photo;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final BooleanPath publicStatus = _super.publicStatus;

    public final SetPath<SubMenu, QSubMenu> subMenus = this.<SubMenu, QSubMenu>createSet("subMenus", SubMenu.class, QSubMenu.class, PathInits.DIRECT2);

    public QFoodMenu(String variable) {
        this(FoodMenu.class, forVariable(variable), INITS);
    }

    public QFoodMenu(Path<? extends FoodMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFoodMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFoodMenu(PathMetadata metadata, PathInits inits) {
        this(FoodMenu.class, metadata, inits);
    }

    public QFoodMenu(Class<? extends FoodMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.photo = inits.isInitialized("photo") ? new QFile(forProperty("photo")) : null;
    }

}

