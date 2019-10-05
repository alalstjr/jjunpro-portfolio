package com.backend.koreanair.dto;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.domain.University;
import com.backend.koreanair.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccountSaveDTO {

    @Size(min=4, max=12, message = "최소 4글자 이상 작성해야 합니다.")
    @NotBlank(message = "아이디를 작성해 주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 작성해 주세요.")
    private String password;

    @Size(min=1, max=15, message = "올바른 이름을 작성해 주세요.")
    @NotBlank(message = "이름을 작성해 주세요.")
    private String username;

    @Size(min=3, max=10, message = "올바른 닉네임을 작성해 주세요.")
    @NotBlank(message = "닉네임을 작성해 주세요.")
    private String nickname;

    private String myUniversity;

    private Set<University> university;

    @Size(min=1, message = "올바른 이메일을 작성해 주세요.")
    private String email;

    private UserRole userRole = UserRole.USER;

    @NotBlank(message = "비밀번호 확인을 작성해 주세요.")
    private String passwordRe;

    public Account toEntity() {
        return Account.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .nickname(nickname)
                .myUniversity(myUniversity)
                .email(email)
                .userRole(userRole)
                .build();
    }
}
