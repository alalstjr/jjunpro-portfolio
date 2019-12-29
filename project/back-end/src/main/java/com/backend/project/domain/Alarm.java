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
public class Alarm extends BaseEntity
{
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long dataId;

    @Column(nullable = false)
    private String dataType;

    @Builder
    private Alarm(Long userId, Long dataId, String dataType)
    {
        this.userId = userId;
        this.dataId = dataId;
        this.dataType = dataType;
    }
}
