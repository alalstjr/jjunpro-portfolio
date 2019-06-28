package com.jjunpro.koreanair.file.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jjunpro.koreanair.board.dto.BoardTask;
import com.jjunpro.koreanair.board.service.BoardTaskService;
import com.jjunpro.koreanair.file.payload.UploadFileResponse;
import com.jjunpro.koreanair.file.service.FileStorageService;

@RestController
@CrossOrigin
public class StoreFileController {

    private static final Logger logger = LoggerFactory.getLogger(StoreFileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
	private BoardTaskService boardTaskService;
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(
    		@RequestParam("file") MultipartFile file,
    		@RequestParam("num") long num
    	) {
        String fileId = fileStorageService.storeFile(file, num);
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileId)
                .toUriString();
        
        String fileType = file.getContentType();
        
        /*
         * 업로드된 이미지 파일중 가장 처음 것을
         * 파일 업로드 동시에 게시판의 미리보기 이미지 업로드
         */
        if(fileType.split("/")[0].equals("image")) {
        	fileStorageService.thumbUpdate(num);
        }
        
        return new UploadFileResponse(fileId, fileDownloadUri,
        		fileType, file.getSize());
    }
    
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(
    		@RequestParam("files") MultipartFile[] files,
    		@RequestParam("num") long num
    	) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, num))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("파일 형식을 결정할 수 없습니다.");
        }

        // 유형을 결정할 수없는 경우 기본 콘텐츠 유형으로 대체
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}