package com.backend.project.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
    private String uploadDirThumbnail;
    private String uploadDirAccount;
}
