package com.jjunpro.project.dto;

import com.jjunpro.project.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 * offsetCount : Front-end 에서 스크롤 로딩시 불러오는 DATA 순서값
 */
@Getter
@Setter
public class SearchDTO {

    private String category;

    @Type(type = "text")
    private String keyword;

    private int offsetCount;

    private Account account;

    private String ifCateA;

    private String ifCateB;

    @Builder
    public SearchDTO(
            String category,
            String keyword,
            int offsetCount,
            String ifCateA,
            String ifCateB,
            Account accountData
    ) {
        this.category = category;
        this.keyword = keyword;
        this.offsetCount = offsetCount;
        this.ifCateA = ifCateA;
        this.ifCateB = ifCateB;
        this.account = accountData;
    }

    @Override
    public String toString() {
        return "SearchDTO{" + "category='" + category + '\'' + ", keyword='" + keyword + '\'' + ", offsetCount=" + offsetCount + ", account=" + account + ", ifCateA='" + ifCateA + '\'' + ", ifCateB='" + ifCateB + '\'' + '}';
    }
}
