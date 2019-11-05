package com.backend.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.List;
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

    private Integer uniStar;

    @Column(nullable = false)
    private String uniIp;

    @ManyToOne
    private Account account;

    @ManyToMany
    private Set<Account> uniLike = new HashSet<>();

    @ManyToMany
    private List<File> files;

    @Builder
    public University(String uniSubject, String uniContent, String uniName, String[] uniTag, Integer uniStar, Set<Account> uniLike, String uniIp, Account account, List<File> files) {
        this.uniSubject = uniSubject;
        this.uniContent = uniContent;
        this.uniName = uniName;
        this.uniTag = uniTag;
        this.uniStar = uniStar;
        this.uniLike = uniLike;
        this.uniIp = uniIp;
        this.account = account;
        this.files = files;
    }
}
