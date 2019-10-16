package com.backend.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends BaseEntity {

    @Column(nullable = false)
    private String stoId;

    @Column(nullable = false)
    private String stoAddress;

    @Builder
    public Store(String stoId, String stoAddress) {
        this.stoId = stoId;
        this.stoAddress = stoAddress;
    }
}