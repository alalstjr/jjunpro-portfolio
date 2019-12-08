package com.backend.project.dto;

import com.backend.project.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@NoArgsConstructor
public class SearchDTO
{
    @Type(type = "text")
    private String keyword;

    @Type(type = "text")
    private String classification;

    private Long offsetCount;

    private Account account;

    private String ifCateA;

    private String ifCateB;
}
