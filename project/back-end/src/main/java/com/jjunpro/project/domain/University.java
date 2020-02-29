package com.jjunpro.project.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class University extends BaseEntity {

    @Column(nullable = false)
    private String uniSubject;

    @Column(nullable = false)
    @Type(type = "text")
    private String uniContent;

    private byte uniAtmosphere;

    private byte uniPrice;

    @Column(nullable = false)
    private String uniName;

    private String uniTag;

    private Integer uniStar;

    @Column(nullable = false)
    private String ip;

    @ManyToOne
    private Account account;

    @ManyToMany
    private Set<Account> uniLike = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<File> files;

    @ManyToOne(cascade = CascadeType.ALL)
    private Store store;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
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
            byte uniAtmosphere,
            byte uniPrice
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

    /* add */
    public void addComment(Comment comment) {
        this
                .getComments()
                .add(comment);
        comment.setUniversity(this);
    }

    /* remove */
    public void removeComment(Comment comment) {
        this
                .getComments()
                .remove(comment);
        comment.setUniversity(null);
    }
}