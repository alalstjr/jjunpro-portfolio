package com.backend.project.service;

import com.backend.project.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileStorageService
{
    public Optional<File> findById(Long id);

    public void fileDelete(File file);
    public void filesDelete(List<File> file);
    public List<File> uploadMultipleFiles(MultipartFile[] files, String domain);
    public File uploadFile(MultipartFile file, String fileRouter);
}
