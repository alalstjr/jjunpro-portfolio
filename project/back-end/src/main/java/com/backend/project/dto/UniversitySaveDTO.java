package com.backend.project.dto;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UniversitySaveDTO {

    @Size(max=50, message = "제목이 너무 깁니다.")
    @NotBlank(message = "제목을 작성해 주세요.")
    private String uniSubject;

    @NotBlank(message = "내용을 작성해 주세요.")
    @Type(type = "text")
    private String uniContent;

    @Size(max=10, message = "대학교 이름이 너무 깁니다.")
    @NotBlank(message = "대학교 이름을 작성해 주세요.")
    private String uniName;

    private String[] uniTag;

    @NotBlank(message = "지역 위치가 없습니다.")
    private String uniLocal;

    private Integer uniStar = 0;

    private Set<Account> uniLike;

    private String uniIp;

    private Account account;

    public University toEntity() {
        return University.builder()
                .uniSubject(uniSubject)
                .uniContent(uniContent)
                .uniName(uniName)
                .uniTag(uniTag)
                .uniLocal(uniLocal)
                .uniStar(uniStar)
                .uniLike(uniLike)
                .uniIp(uniIp)
                .account(account)
                .build();
    }
}
