package com.backend.koreanair.projection;

import com.backend.koreanair.enums.UserRole;

import java.time.LocalDateTime;

public interface AccountPublic {

    String getId();
    String getUsername();
    UserRole getUserRole();
    LocalDateTime getCreatedDate();
    LocalDateTime getModifiedDate();
}
