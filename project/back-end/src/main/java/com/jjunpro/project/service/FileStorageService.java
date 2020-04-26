package com.jjunpro.project.service;

import com.jjunpro.project.domain.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * List<File> uploadMultipleFiles() :
 * { fileSizeDB } 업로드하는 Domain 대상의 file 갯수 새로운 값일 경우 default 값은 0 입니다.
 * { files } 클라이언트에서 받은 file 의 갯수
 * { domain } 업로드하는 Domain 대상의 이름
 */
public interface FileStorageService {

    Optional<File> findById(Long id);

    void fileDelete(File file);

    void filesDelete(List<File> file);

    void deleteFileFilter(Long[] removeFiles);

    List<File> uploadMultipleFiles(int fileSizeDB, MultipartFile[] files, String domain);

    File uploadFile(MultipartFile file, String fileRouter);
}
