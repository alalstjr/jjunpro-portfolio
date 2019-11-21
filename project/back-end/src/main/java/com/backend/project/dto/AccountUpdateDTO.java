package com.backend.project.dto;

import com.backend.project.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AccountUpdateDTO {

    private Long id;

    @Size(min=3, max=10, message = "올바른 닉네임을 작성해 주세요.")
    @NotBlank(message = "닉네임을 작성해 주세요.")
    private String nickname;

    @Size(min=1, message = "올바른 이메일을 작성해 주세요.")
    private String email;

    @Size(max = 3, message = "태그는 3개만 입력 가능합니다.")
    private String[] urlList = new String[3];

    public Account toEntity() {
        return Account.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .urlList(urlList)
                .build();
    }
}