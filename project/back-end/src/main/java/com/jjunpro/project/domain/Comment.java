package com.jjunpro.project.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
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

    @Builder
    public Comment(Long id, String content, String ip, Account account, University university) {
        this.id = id;
        this.content = content;
        this.ip = ip;
        this.account = account;
        this.university = university;
    }

    @Override
    public String toString() {
        return "Comment{" + "content='" + content + '\'' + ", ip='" + ip + '\'' + ", account=" + account + ", university=" + university + '}';
    }
}
