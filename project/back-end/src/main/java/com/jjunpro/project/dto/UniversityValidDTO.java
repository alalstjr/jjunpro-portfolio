package com.jjunpro.project.dto;

import com.jjunpro.project.annotation.UserDataMatch;
import com.jjunpro.project.annotation.UserExistence;
import com.jjunpro.project.enums.DomainType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UserExistence(id = "id")
@UserDataMatch(id = "id", domain = DomainType.UNIVERSITY)
public class UniversityValidDTO {

    private Long id;

    /* UniversityConverter.class 인스턴스 시킬때 필요한 생성자 */
    public UniversityValidDTO(Long id) {
        this.id = id;
    }
}
