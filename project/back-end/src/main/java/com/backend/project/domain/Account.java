package com.backend.project.domain;

import com.backend.project.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToOne
    private File photo;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    @Builder
    public Account(Long id, String userId, String password, String nickname, String myUniversity, String email, String[] urlList, File photo) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.myUniversity = myUniversity;
        this.email = email;
        this.urlList = urlList;
        this.photo = photo;
    }
}
