package com.backend.project.dto;

import com.backend.project.annotation.UserDataMatch;
import com.backend.project.annotation.UserExistence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@UserExistence(id = "id")
@UserDataMatch(id = "id")
public class CommentDTO {

    private Long id;

    // CommentConverter.class 인스턴스 시킬때 필요한 생성자
    public CommentDTO(Long id) {
        this.id = id;
    }
}
