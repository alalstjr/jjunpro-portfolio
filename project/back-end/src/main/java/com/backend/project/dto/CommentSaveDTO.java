package com.backend.project.dto;

import com.backend.project.domain.Account;
import com.backend.project.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CommentSaveDTO {

    private Long uniId;

    private String ip;

    @Size(min=4, max=255, message = "최소 4글자 이상 내용을 작성해야 합니다.")
    @NotBlank(message = "내용을 작성해 주세요.")
    private String content;

    private Account account;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .ip(ip)
                .account(account)
                .build();
    }
}