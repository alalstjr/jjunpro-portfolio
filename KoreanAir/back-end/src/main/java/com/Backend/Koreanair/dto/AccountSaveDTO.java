package com.backend.koreanair.dto;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.domain.File;
import com.backend.koreanair.domain.University;
import com.backend.koreanair.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccountSaveDTO {
    private String userId;
    private String password;
    private String username;
    private String nickname;
    private String myUniversity;
    private Set<University> university;
    private Set<File> photo;
    private String email;
    private UserRole userRole = UserRole.USER;

    public Account toEntity() {
        return Account.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .nickname(nickname)
                .myUniversity(myUniversity)
                .university(university)
                .email(email)
                .userRole(userRole)
                .build();
    }
}
