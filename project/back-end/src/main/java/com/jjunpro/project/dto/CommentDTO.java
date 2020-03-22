package com.jjunpro.project.dto;

import com.jjunpro.project.validator.UserDataMatch;
import com.jjunpro.project.validator.UserExistence;
import com.jjunpro.project.enums.DomainType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@UserExistence(id = "id")
@UserDataMatch(id = "id", domain = DomainType.COMMENT)
public class CommentDTO {

    private Long id;

    /* CommentConverter.class 인스턴스 시킬때 필요한 생성자 */
    public CommentDTO(Long id) {
        this.id = id;
    }
}