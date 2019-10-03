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
public class Account extends BaseDate {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String myUniversity;

//    @OneToMany
//    @Column(nullable = true)
//    private Set<File> photo = new HashSet<>();

    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Builder
    public Account(String userId, String password, String username, String nickname, String myUniversity, String email, UserRole userRole) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.myUniversity = myUniversity;
        this.email = email;
        this.userRole = userRole;
    }
}
