package com.jjunpro.project.domain;

import com.jjunpro.project.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;

@Setter
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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToOne
    private Store store;

    @OneToOne(cascade = CascadeType.ALL)
    private File photo;

    @Builder
    public Account(
            Long id,
            String username,
            String password,
            String nickname,
            String myUniversity,
            String email,
            String[] urlList,
            Store store,
            UserRole role,
            File photo
    ) {
        this.store = store;
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.myUniversity = myUniversity;
        this.email = email;
        this.urlList = urlList;
        this.role = role;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Account{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", nickname='" + nickname + '\'' + ", myUniversity='" + myUniversity + '\'' + ", email='" + email + '\'' + ", urlList=" + Arrays.toString(urlList) + ", role=" + role + '}';
    }
}
