package com.backend.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Alarm extends BaseEntity {
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long dataId;

    @Column(nullable = false)
    private String dataType;

    @Type(type = "text")
    private String dataContent;

    private String writeId;

    @Builder
    private Alarm(Long userId, Long dataId, String dataType, String dataContent, String writeId) {
        this.userId = userId;
        this.dataId = dataId;
        this.dataType = dataType;
        this.dataContent = dataContent;
        this.writeId = writeId;
    }
}
