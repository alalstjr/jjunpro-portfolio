package com.backend.project.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity extends BaseDate {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Boolean publicStatus = true;

    @Column(nullable = false)
    private Boolean controlStatus = false;
}
