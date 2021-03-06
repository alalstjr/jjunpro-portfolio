package com.jjunpro.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity {

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private Integer fileDivision;

    @Column(nullable = false)
    private String fileOriginal;

    @Column(nullable = false)
    private String fileThumbnail;

    @Builder
    public File(Long id, String filename, long fileSize, String fileType, Integer fileDivision, String fileOriginal, String fileThumbnail) {
        this.id = id;
        this.filename = filename;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileDivision = fileDivision;
        this.fileOriginal = fileOriginal;
        this.fileThumbnail = fileThumbnail;
    }
}

