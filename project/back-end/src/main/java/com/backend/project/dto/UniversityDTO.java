package com.backend.project.dto;

import com.backend.project.annotation.UserDataMatch;
import com.backend.project.annotation.UserExistence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UserExistence(id = "id")
@UserDataMatch(id = "id", domain = "university")
public class UniversityDTO {

    private Long id;

    // UniversityConverter.class 인스턴스 시킬때 필요한 생성자
    public UniversityDTO(Long id) {
        this.id = id;
    }
}
