package com.backend.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Column(nullable = false)
    @Type(type = "text")
    private String content;

    @Column(nullable = false)
    private String ip;

    @ManyToOne
    private Account account;

    @Builder
    public Comment(String content, String ip, Account account) {
        this.content = content;
        this.ip      = ip;
        this.account = account;
    }
}
