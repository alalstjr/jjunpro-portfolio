package com.jjunpro.project.domain;

import com.jjunpro.project.enums.AlarmType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long dataId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AlarmType dataType;

    @Type(type = "text")
    private String dataContent;

    private String writeId;

    @Builder
    private Alarm(Long userId, Long dataId, AlarmType dataType, String dataContent, String writeId) {
        this.userId = userId;
        this.dataId = dataId;
        this.dataType = dataType;
        this.dataContent = dataContent;
        this.writeId = writeId;
    }
}