package com.backend.project.dto;

import com.backend.project.domain.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileDTO {
    private Long id;
    private String filename;
    private String fileType;
    private long fileSize;
    private Integer fileDivision;
    private String fileThumbnail;

    public File toEntity() {
        return File.builder()
                .id(id)
                .filename(filename)
                .fileType(fileType)
                .fileSize(fileSize)
                .fileDivision(fileDivision)
                .fileThumbnail(fileThumbnail)
                .build();
    }
}
