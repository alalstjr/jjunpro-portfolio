package com.backend.koreanair.projection;

import com.backend.koreanair.enums.UserRole;

import java.time.LocalDateTime;

public interface AccountPublic {

    String getId();
    String getUser_id();
    String getUsername();
    UserRole getUser_role();
    LocalDateTime getCreated_date();
    LocalDateTime getModified_date();
}
