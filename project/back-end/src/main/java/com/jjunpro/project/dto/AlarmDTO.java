package com.jjunpro.project.dto;

import com.jjunpro.project.validator.UserDataMatch;
import com.jjunpro.project.validator.UserExistence;
import com.jjunpro.project.domain.Alarm;
import com.jjunpro.project.enums.AlarmType;
import com.jjunpro.project.enums.DomainType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@UserExistence(id = "id")
@UserDataMatch(id = "id", domain = DomainType.ALARM)
public class AlarmDTO {

    private Long id;

    private Long userId;

    private Long dataId;

    private AlarmType dataType;

    private String dataContent;

    private String writeId;

    /* AlarmConverter.class 인스턴스 시킬때 필요한 생성자 */
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

    @Builder
    public AlarmDTO(
            Long id,
            Long userId,
            Long dataId,
            AlarmType dataType,
            String dataContent,
            String writeId
    ) {
        this.id = id;
        this.userId = userId;
        this.dataId = dataId;
        this.dataType = dataType;
        this.dataContent = dataContent;
        this.writeId = writeId;
    }
}