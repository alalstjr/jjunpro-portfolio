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
public class Store extends BaseEntity {

    @Column(nullable = false)
    private String stoId;

    @Column(nullable = false)
    private String stoName;

    @Column(nullable = false)
    private String stoAddress;

    @Column(nullable = false)
    private String stoUrl;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<University> stoUniList = new HashSet<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<FoodMenu> foodMenu = new HashSet<>();

    @OneToOne(mappedBy = "store")
    private Account account;

    @Builder
    public Store(String stoId, String stoName, String stoAddress, String stoUrl) {
        this.stoId = stoId;
        this.stoName = stoName;
        this.stoAddress = stoAddress;
        this.stoUrl = stoUrl;
    }

    @Override
    public String toString() {
        return "Store{" + "stoId='" + stoId + '\'' + ", stoName='" + stoName + '\'' + ", stoAddress='" + stoAddress + '\'' + ", stoUrl='" + stoUrl + '\'' + ", stoUniList=" + stoUniList + ", foodMenu=" + foodMenu + '}';
    }

    /* add */
    public void addUniversity(University university) {
        this
                .getStoUniList()
                .add(university);
        university.setStore(this);
    }
}