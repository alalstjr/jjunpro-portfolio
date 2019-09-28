package com.backend.koreanair.dto;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountSaveDTO {
    private String userId;
    private String password;
    private String username;
    private UserRole userRole = UserRole.USER;

    public Account toEntity() {
        return Account.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .userRole(userRole)
                .build();
    }
}
