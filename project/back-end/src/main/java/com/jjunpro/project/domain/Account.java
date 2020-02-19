package com.jjunpro.project.domain;

import com.jjunpro.project.enums.UserRole;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String myUniversity;

    @Column(unique = true)
    private String email;

    private String[] urlList;

    //@OneToOne
    //private File photo;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Builder
    public Account(
            Long id,
            String username,
            String password,
            String nickname,
            String myUniversity,
            String email,
            String[] urlList,
            UserRole role
            //File photo
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.myUniversity = myUniversity;
        this.email = email;
        this.urlList = urlList;
        this.role = role;
        //this.photo = photo;
    }

    @Override
    public String toString() {
        return "Account{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", nickname='" + nickname + '\'' + ", myUniversity='" + myUniversity + '\'' + ", email='" + email + '\'' + ", urlList=" + Arrays.toString(urlList) + ", role=" + role + '}';
    }
}
