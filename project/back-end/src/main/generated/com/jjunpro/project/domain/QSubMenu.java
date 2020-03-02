package com.jjunpro.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubMenu is a Querydsl query type for SubMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubMenu extends EntityPathBase<SubMenu> {

    private static final long serialVersionUID = 1358800113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubMenu subMenu = new QSubMenu("subMenu");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final BooleanPath controlStatus = _super.controlStatus;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QFoodMenu foodMenu;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final BooleanPath publicStatus = _super.publicStatus;

    public QSubMenu(String variable) {
        this(SubMenu.class, forVariable(variable), INITS);
    }

    public QSubMenu(Path<? extends SubMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubMenu(PathMetadata metadata, PathInits inits) {
        this(SubMenu.class, metadata, inits);
    }

    public QSubMenu(Class<? extends SubMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.foodMenu = inits.isInitialized("foodMenu") ? new QFoodMenu(forProperty("foodMenu"), inits.get("foodMenu")) : null;
    }

}

