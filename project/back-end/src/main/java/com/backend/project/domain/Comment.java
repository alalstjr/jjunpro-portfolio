package com.backend.project.domain;

import com.backend.project.annotation.UserDataMatch;
import com.backend.project.annotation.UserExistence;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Entity
@UserExistence(id = "id")
@UserDataMatch(id = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Column(nullable = false)
    @Type(type = "text")
    private String content;

    @Column(nullable = false)
    private String ip;

    @ManyToOne
    private Account account;

    @ManyToOne
    private University university;

    // CommentConverter.class 인스턴스 시킬때 필요한 생성자
    public Comment(Long id) {
        this.id = id;
    }

    @Builder
    public Comment(Long id, String content, String ip, Account account, University university) {
        this.id = id;
        this.content = content;
        this.ip = ip;
        this.account = account;
        this.university = university;
    }
}
