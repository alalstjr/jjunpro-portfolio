package com.backend.koreanair.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(nullable = false)
    private Boolean uniState;

    @Column(nullable = false)
    private String uniLocal;

    @ManyToOne
    private Account account;

    //    @OneToMany
    //    @Column(nullable = true)
    //    private Set<File> photo = new HashSet<>();

    @Builder
    public University(Long id, String uniSubject, String uniName, String uniTag, Boolean uniState, String uniLocal, Account account) {
        this.id = id;
        this.uniSubject = uniSubject;
        this.uniName = uniName;
        this.uniTag = uniTag;
        this.uniState = uniState;
        this.uniLocal = uniLocal;
        this.account = account;
    }
}
