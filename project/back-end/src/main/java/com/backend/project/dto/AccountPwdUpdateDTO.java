package com.backend.project.dto;

import com.backend.project.annotation.PasswordMatch;
import com.backend.project.annotation.UserDataMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@UserDataMatch(id = "id")
@PasswordMatch(password = "password", passwordRe = "passwordRe", oldPassword = "oldPassword", encoder = true)
public class AccountPwdUpdateDTO {
    private Long id;

    @NotBlank(message = "새로운 비밀번호를 작성해 주세요.")
    private String password;

    @NotBlank(message = "새로운 비밀번호 확인을 작성해 주세요.")
    private String passwordRe;

    @NotBlank(message = "이전 비밀번호를 작성해 주세요.")
    private String oldPassword;
}
