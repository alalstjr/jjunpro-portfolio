package com.backend.koreanair.projection;

import com.backend.koreanair.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface AccountPublic {

    Long getId();
    String getUser_id();
    String getUsername();
    UserRole getUser_role();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime getCreated_date();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime getModified_date();
}
