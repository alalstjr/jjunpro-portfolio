package com.backend.project.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class BaseEntity extends BaseDate {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    private Boolean publicStatus = true;

    @Column(nullable = false)
    private Boolean controlStatus = false;
}
