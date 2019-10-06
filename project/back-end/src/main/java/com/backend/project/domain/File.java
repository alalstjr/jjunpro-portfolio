package com.backend.project.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class File extends BaseDate {

    @Id
    @GeneratedValue
    private String id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private int fileDivision;

    @Column(nullable = false)
    private int fileDownload;
}
