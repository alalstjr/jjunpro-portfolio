package com.backend.koreanair.domain;

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
public class University extends BaseDate {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String uniSubject;

    @Column(nullable = false)
    private String uniName;

    @Column(nullable = true)
    private String uniTag;

    @OneToMany
    @Column(nullable = true)
    private Set<File> photo = new HashSet<>();

    @Column(nullable = false)
    private Boolean uniState;

    @ManyToOne
    private Account account;

    @Builder
    public University(String uniSubject, String uniName, String uniTag, Set<File> photo, Boolean uniState, Account account) {
        this.uniSubject = uniSubject;
        this.uniName = uniName;
        this.uniTag = uniTag;
        this.photo = photo;
        this.uniState = uniState;
        this.account = account;
    }
}
