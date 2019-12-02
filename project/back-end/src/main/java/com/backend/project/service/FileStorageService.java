package com.backend.project.service;

import com.backend.project.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    public void fileDelete(File file, String domain);
    public void filesDelete(List<File> file, String domain);
    public List<File> uploadMultipleFiles(MultipartFile[] files, String domain);
    public File uploadFile(MultipartFile file, String fileRouter);
}
