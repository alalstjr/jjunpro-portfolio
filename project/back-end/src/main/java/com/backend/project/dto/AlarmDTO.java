package com.backend.project.dto;

import com.backend.project.annotation.UserDataMatch;
import com.backend.project.annotation.UserExistence;
import com.backend.project.domain.Alarm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@UserExistence(id = "id")
@UserDataMatch(id = "id", domain = "alarm")
public class AlarmDTO {

    private Long id;

    private Long userId;

    private Long dataId;

    private String dataType;

    private String dataContent;

    private String writeId;

    // AlarmConverter.class 인스턴스 시킬때 필요한 생성자
    public AlarmDTO(Long id) {
        this.id = id;
    }

    public Alarm toEntity() {
        return Alarm
                .builder()
                .userId(userId)
                .dataId(dataId)
                .dataType(dataType)
                .dataContent(dataContent)
                .writeId(writeId)
                .build();
    }
}
