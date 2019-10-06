package com.backend.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SecurityFormLoginDTO {

    private String userId;

    private String password;
}
