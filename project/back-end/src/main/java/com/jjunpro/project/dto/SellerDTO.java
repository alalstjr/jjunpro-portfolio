package com.jjunpro.project.dto;

import com.jjunpro.project.domain.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SellerDTO {

    @NotBlank(message = "이름을 작성해 주세요.")
    private String name;

    @NotBlank(message = "핸드폰 번호를 작성해 주세요.")
    private String phoneNumber;

    /* STORE DATA */
    @NotBlank(message = "가게정보를 작성해 주세요.")
    private String stoId;

    @NotBlank(message = "가게정보를 작성해 주세요.")
    private String stoName;

    @NotBlank(message = "가게정보를 작성해 주세요.")
    private String stoAddress;

    private String stoUrl;

    @ManyToOne
    private Account account;

    @Builder
    public SellerDTO(
            String name,
            String phoneNumber,
            String stoId,
            String stoName,
            String stoAddress,
            String stoUrl,
            Account account
    ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.stoId = stoId;
        this.stoName = stoName;
        this.stoAddress = stoAddress;
        this.stoUrl = stoUrl;
        this.account = account;
    }
}
