package com.jjunpro.project.controller;

import com.jjunpro.project.service.FileStorageServiceImpl;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private static final Logger                 logger = LoggerFactory
            .getLogger(com.jjunpro.project.controller.FileController.class);
    private final        FileStorageServiceImpl fileStorageService;

    @GetMapping("/{domain}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String domain,
            @PathVariable String fileName,
            HttpServletRequest request
    ) {
        // Load file as Resource
        Resource resource = this.fileStorageService.loadFileAsResource(domain + '/' + fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
