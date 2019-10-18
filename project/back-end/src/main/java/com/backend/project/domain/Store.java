package com.backend.project.domain;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends BaseEntity {

    @Column(nullable = false)
    private String stoId;

    @Column(nullable = false)
    private String stoAddress;

    @OneToMany
    private Set<University> stoUniList = new HashSet<>();

    @Builder
    public Store(String stoId, String stoAddress, Set<University> stoUniList) {
        this.stoId = stoId;
        this.stoAddress = stoAddress;
        this.stoUniList = stoUniList;
    }
}