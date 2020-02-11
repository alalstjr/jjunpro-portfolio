package com.backend.project.dto;

import com.backend.project.annotation.UserDataMatch;
import com.backend.project.annotation.UserExistence;
import com.backend.project.domain.Alarm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@UserExistence(id = "id")
@UserDataMatch(id = "id")
public class AlarmDTO {

    private Long id;

    @NotBlank(message = "userId 값은 필수 입니다.")
    private Long userId;

    @NotBlank(message = "dataId 값은 필수 입니다.")
    private Long dataId;

    @NotBlank(message = "dataType 값은 필수 입니다.")
    private String dataType;

    @NotBlank(message = "dataContent 값은 필수 입니다.")
    private String dataContent;

    @NotBlank(message = "writeId 값은 필수 입니다.")
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
