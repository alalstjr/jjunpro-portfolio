package com.jjunpro.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodMenu extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @OneToOne
    private File photo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<SubMenu> subMenus = new HashSet<>();

    @Builder
    public FoodMenu(
            String name,
            String content,
            Integer price,
            File photo,
            Set<SubMenu> subMenus
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.photo = photo;
        this.subMenus = subMenus;
    }
}
