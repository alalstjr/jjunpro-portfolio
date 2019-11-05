package com.backend.project.dto;

import com.backend.project.domain.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileDTO {
    private String fileName;
    private String fileType;
    private long fileSize;
    private Integer fileDivision;

    public File toEntity() {
        return File.builder()
                .fileName(fileName)
                .fileType(fileType)
                .fileSize(fileSize)
                .fileDivision(fileDivision)
                .build();
    }
}
