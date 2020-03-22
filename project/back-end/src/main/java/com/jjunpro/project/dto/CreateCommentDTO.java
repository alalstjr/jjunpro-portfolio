package com.jjunpro.project.dto;

import com.jjunpro.project.validator.UserExistence;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Comment;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.util.AccountUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Getter
@Setter
@UserExistence(id = "id")
@NoArgsConstructor
public class CreateCommentDTO {

    private Long id;

    //@NotBlank(message = "게시글의 ID는 필수 값입니다.")
    private Long uniId;

    private String ip;

    @Size(min = 1, max = 255, message = "최소 1글자 이상 내용을 작성해야 합니다.")
    @NotBlank(message = "내용을 작성해 주세요.")
    private String content;

    private Account account;

    private University university;

    /* User Ip and User Account 기본값을 설정합니다. */
    public void defaultSetting(
            String userIp,
            AccountUtil accountUtil,
            Authentication authentication
    ) {
        /* Account Info */
        Optional<Account> accountData = accountUtil.accountInfo(authentication);

        accountData.ifPresent(value -> this.account = value);
        this.ip = userIp;
    }

    public Comment toEntity() {
        return Comment
                .builder()
                .id(id)
                .content(content)
                .ip(ip)
                .account(account)
                .university(university)
                .build();
    }

    @Builder
    public CreateCommentDTO(
            Long id,
            Long uniId,
            String ip,
            String content,
            Account account,
            University university
    ) {
        this.id = id;
        this.uniId = uniId;
        this.ip = ip;
        this.content = content;
        this.account = account;
        this.university = university;
    }
}
