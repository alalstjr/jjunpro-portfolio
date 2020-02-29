package com.jjunpro.project.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubMenu extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    private FoodMenu foodMenu;
}
