package com.jjunpro.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

    @OneToMany
    private Set<University> stoUniList = new HashSet<>();

    @Builder
    public Store(String stoId, String stoName, String stoAddress, String stoUrl, Set<University> stoUniList) {
        this.stoId = stoId;
        this.stoName = stoName;
        this.stoAddress = stoAddress;
        this.stoUrl = stoUrl;
        this.stoUniList = stoUniList;
    }
}