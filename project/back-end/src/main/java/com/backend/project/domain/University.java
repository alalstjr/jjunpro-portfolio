package com.backend.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class University extends BaseEntity {

    @Column(nullable = false)
    private String uniSubject;

    @Column(nullable = false)
    @Type(type = "text")
    private String uniContent;

    @Column(nullable = false)
    private String uniName;

    private String[] uniTag;

    @Column(nullable = false)
    private String uniLocal;

    private Integer uniStar;

    @Column(nullable = false)
    private String uniIp;

    @ManyToOne
    private Account account;

    @OneToMany
    private Set<Account> uniLike = new HashSet<>();

    @Builder
    public University(String uniSubject, String uniContent, String uniName, String[] uniTag, String uniLocal, Integer uniStar, Set<Account> uniLike, String uniIp, Account account) {
        this.uniSubject = uniSubject;
        this.uniContent = uniContent;
        this.uniName = uniName;
        this.uniTag = uniTag;
        this.uniLocal = uniLocal;
        this.uniStar = uniStar;
        this.uniLike = uniLike;
        this.uniIp = uniIp;
        this.account = account;
    }
}
