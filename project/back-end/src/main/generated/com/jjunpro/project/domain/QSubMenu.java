package com.jjunpro.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubMenu is a Querydsl query type for SubMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubMenu extends EntityPathBase<SubMenu> {

    private static final long serialVersionUID = 1358800113L;

    public static final QSubMenu subMenu = new QSubMenu("subMenu");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final BooleanPath controlStatus = _super.controlStatus;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final BooleanPath publicStatus = _super.publicStatus;

    public QSubMenu(String variable) {
        super(SubMenu.class, forVariable(variable));
    }

    public QSubMenu(Path<? extends SubMenu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubMenu(PathMetadata metadata) {
        super(SubMenu.class, metadata);
    }

}

