package com.backend.project.service;

import com.backend.project.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * uploadMultipleFiles :
 * { fileSizeDB } 업로드하는 Domain 대상의 file 갯수 새로운 값일 경우 default 값은 0 입니다.
 * { files } 클라이언트에서 받은 file 의 갯수
 * { domain } 업로드하는 Domain 대상의 이름
 */
public interface FileStorageService {
    public Optional<File> findById(Long id);

    public void fileDelete(File file);

    public void filesDelete(List<File> file);

    public void deleteFileFilter(Long[] removeFiles);

    public List<File> uploadMultipleFiles(int fileSizeDB, MultipartFile[] files, String domain);

    public File uploadFile(MultipartFile file, String fileRouter);
}
