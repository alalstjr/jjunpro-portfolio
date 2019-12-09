package com.backend.project.dto;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
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

    @NotBlank(message = "비밀번호 확인을 작성해 주세요.")
    private String passwordRe;

    @Size(min=3, max=10, message = "올바른 닉네임을 작성해 주세요.")
    @NotBlank(message = "닉네임을 작성해 주세요.")
    private String nickname;

    private String myUniversity;

    private Set<University> university;

    private String email;

    private String[] urlList;

    public Account toEntity() {
        return Account.builder()
                .userId(userId)
                .password(password)
                .nickname(nickname)
                .myUniversity(myUniversity)
                .email(email)
                .urlList(urlList)
                .build();
    }
}
