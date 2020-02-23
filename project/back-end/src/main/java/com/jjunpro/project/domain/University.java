package com.jjunpro.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class University extends BaseEntity {

    @Column(nullable = false)
    private String uniSubject;

    @Column(nullable = false)
    @Type(type = "text")
    private String uniContent;

    private String uniAtmosphere;

    private String uniPrice;

    @Column(nullable = false)
    private String uniName;

    private String uniTag;

    private Integer uniStar;

    @Column(nullable = false)
    private String ip;

    @ManyToOne
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Account> uniLike = new HashSet<>();

    @ManyToMany
    private List<File> files;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

    @Builder
    public University(
            Long id,
            String uniSubject,
            String uniContent,
            String uniName,
            String uniTag,
            Integer uniStar,
            Set<Account> uniLike,
            String ip,
            Account account,
            List<File> files,
            Set<Comment> comments,
            String uniAtmosphere,
            String uniPrice
    ) {
        this.id = id;
        this.uniSubject = uniSubject;
        this.uniContent = uniContent;
        this.uniName = uniName;
        this.uniTag = uniTag;
        this.uniStar = uniStar;
        this.uniLike = uniLike;
        this.ip = ip;
        this.account = account;
        this.files = files;
        this.comments = comments;
        this.uniAtmosphere = uniAtmosphere;
        this.uniPrice = uniPrice;
    }

    @Override
    public String toString() {
        return "University{" + "uniSubject='" + uniSubject + '\'' + ", uniContent='" + uniContent + '\'' + ", uniAtmosphere='" + uniAtmosphere + '\'' + ", uniPrice='" + uniPrice + '\'' + ", uniName='" + uniName + '\'' + ", uniTag='" + uniTag + '\'' + ", uniStar=" + uniStar + ", ip='" + ip + '\'' + ", account=" + account + '}';
    }
}