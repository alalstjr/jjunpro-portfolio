package com.backend.project.domain;

import javax.persistence.*;

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
