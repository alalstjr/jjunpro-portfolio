package com.backend.koreanair.dto;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.domain.University;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UniversitySaveDTO {

    private String uniSubject;
    private String uniName;
    private String uniTag;
    private Boolean uniState;
    private Account account;
    private String uniLocal;

    public University toEntity() {
        return University.builder()
                .uniSubject(uniSubject)
                .uniName(uniName)
                .account(account)
                .uniTag(uniTag)
                .uniState(uniState)
                .uniLocal(uniLocal)
                .build();
    }
}
