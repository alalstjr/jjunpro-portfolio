package com.jjunpro.koreanair.dto;

import lombok.Data;

@Data
public class StoreFileDTO {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public StoreFileDTO(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}