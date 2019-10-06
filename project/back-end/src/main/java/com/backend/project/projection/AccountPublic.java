package com.backend.project.projection;

import com.backend.project.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface AccountPublic {

    Long getId();
    String getUser_id();
    String getNickname();
    UserRole getUser_role();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime getCreated_date();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime getModified_date();
}
