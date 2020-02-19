package com.jjunpro.project.dto;

import com.jjunpro.project.annotation.EmailMatch;
import com.jjunpro.project.annotation.NickNameMatch;
import com.jjunpro.project.annotation.UserDataMatch;
import com.jjunpro.project.annotation.WordFilter;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@UserDataMatch(id = "id", domain = "account")
public class UpdateAccountDTO {

    private Long id;

    @Size(min = 3, max = 10, message = "최소 3글자 이상 10글자 이하로 작성해야 합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "닉네임은 한글 영문 숫자만 입력 가능합니다.")
    @NotBlank(message = "닉네임을 작성해 주세요.")
    @WordFilter
    @NickNameMatch
    private String nickname;

    @Email
    @EmailMatch
    private String email;

    @Size(max = 3, message = "태그는 3개만 입력 가능합니다.")
    private String[] urlList;

    // 서버에 저장된 File 의 정보 file -> fileData 순으로 엔티티에 변환되어 저장
    //private File fileData;

    // 클라이언트에서 받은 File
    private MultipartFile file;

    /* UPDATE 전달받아야 하는 정보 */
    private String username;

    private String password;

    private String myUniversity;

    private UserRole role;

    public Account toEntity() {
        return Account
                .builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .urlList(urlList)
                //.photo(fileData)
                .username(username)
                .password(password)
                .myUniversity(myUniversity)
                .role(role)
                .build();
    }
}
