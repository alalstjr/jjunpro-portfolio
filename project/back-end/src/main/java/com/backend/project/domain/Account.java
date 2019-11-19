package com.backend.project.domain;

import com.backend.project.enums.UserRole;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String myUniversity;

    @Column(unique = true)
    private String email;

    private String[] urlList;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    @Builder
    public Account(Long id, String userId, String password, String nickname, String myUniversity, String email, String[] urlList) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.myUniversity = myUniversity;
        this.email = email;
        this.urlList = urlList;
    }
}
