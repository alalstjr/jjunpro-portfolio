package com.jjunpro.project.dto;

import com.jjunpro.project.annotation.PasswordMatch;
import com.jjunpro.project.annotation.UserDataMatch;
import com.jjunpro.project.enums.DomainType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@UserDataMatch(id = "id", domain = DomainType.ACCOUNT)
@PasswordMatch(password = "password", passwordRe = "passwordRe", oldPassword = "oldPassword", encoder = true)
public class UpdateAccountPwdDTO {

    private Long id;

    @NotBlank(message = "새로운 비밀번호를 작성해 주세요.")
    private String password;

    @NotBlank(message = "새로운 비밀번호 확인을 작성해 주세요.")
    private String passwordRe;

    @NotBlank(message = "이전 비밀번호를 작성해 주세요.")
    private String oldPassword;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}