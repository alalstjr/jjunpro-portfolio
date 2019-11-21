package com.backend.project.projection;

import com.backend.project.domain.File;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountPublic {
    private Long id;
    private String userId;
    private String nickname;
    private String myUniversity;
    private String email;
    private String[] urlList;
    private File photo;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;
}
