package com.backend.koreanair.domain;

import com.backend.koreanair.dto.UniversitySaveDTO;
import com.backend.koreanair.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "account")
    @Column(nullable = true)
    private Set<University> university = new HashSet<>();

//    @OneToMany
//    @Column(nullable = true)
//    private Set<File> photo = new HashSet<>();

    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Builder
    public Account(String userId, String password, String username, String nickname, String myUniversity, Set<University> university, String email, UserRole userRole) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.myUniversity = myUniversity;
        this.university = university;
        this.email = email;
        this.userRole = userRole;
    }

    public void addUniversity(UniversitySaveDTO university) {
        this.getUniversity().add(university.toEntity());
        university.setAccount(this);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", myUniversity='" + myUniversity + '\'' +
                ", university=" + university +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
