package com.backend.project.dto;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AccountUpdateDTO {

    private Long id;

    @Size(min=3, max=10, message = "올바른 닉네임을 작성해 주세요.")
    @NotBlank(message = "닉네임을 작성해 주세요.")
    private String nickname;

    @Size(min=1, message = "올바른 이메일을 작성해 주세요.")
    private String email;

    @Size(max = 3, message = "태그는 3개만 입력 가능합니다.")
    private String[] urlList = new String[3];

    // 서버에 저장된 File 의 정보 file -> fileData 순으로 엔티티에 변환되어 저장
    private File fileData;

    // 클라이언트에서 받은 File
    private MultipartFile file;

    public Account toEntity() {
        return Account.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .urlList(urlList)
                .photo(fileData)
                .build();
    }
}
