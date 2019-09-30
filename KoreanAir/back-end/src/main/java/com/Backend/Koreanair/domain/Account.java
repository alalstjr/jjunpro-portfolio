package com.backend.koreanair.domain;

import com.backend.koreanair.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Builder
    public Account(Long id, String userId, String username, String password, UserRole userRole) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}
