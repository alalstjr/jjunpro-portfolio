package com.backend.koreanair.dto;

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
