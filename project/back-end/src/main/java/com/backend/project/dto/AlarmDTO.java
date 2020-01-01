package com.backend.project.dto;

import com.backend.project.domain.Alarm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AlarmDTO
{
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

    public Alarm toEntity()
    {
        return Alarm.builder()
                .userId(userId)
                .dataId(dataId)
                .dataType(dataType)
                .dataContent(dataContent)
                .writeId(writeId)
                .build();
    }
}