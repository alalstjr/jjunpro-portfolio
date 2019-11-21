package com.backend.project.service;

import com.backend.project.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    public List<File> uploadMultipleFiles(MultipartFile[] files, String fileRouter);
    public File uploadFile(MultipartFile file, String fileRouter);
}
