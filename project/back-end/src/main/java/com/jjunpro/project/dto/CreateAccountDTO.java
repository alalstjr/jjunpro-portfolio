package com.jjunpro.project.dto;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.enums.ColumnType;
import com.jjunpro.project.enums.UserRole;
import com.jjunpro.project.validator.DataMatch;
import com.jjunpro.project.validator.PasswordMatch;
import com.jjunpro.project.validator.WordFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatch(password = "password", passwordRe = "passwordRe")
public class CreateAccountDTO {

    @Size(min = 4, max = 12, message = "최소 4글자 이상 작성해야 합니다.")
    @NotBlank(message = "아이디를 작성해 주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문 또는 숫자만 입력 가능합니다.")
    @DataMatch(message = "이미 존재하는 아이디 입니다.", column = ColumnType.USERNAME)
    private String username;

    @NotBlank(message = "비밀번호를 작성해 주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 작성해 주세요.")
    private String passwordRe;

    @Size(min = 3, max = 10, message = "최소 3글자 이상 10글자 이하로 작성해야 합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "닉네임은 한글 영문 숫자만 입력 가능합니다.")
    @NotBlank(message = "닉네임을 작성해 주세요.")
    @DataMatch(message = "이미 존재하는 닉네임 입니다.", column = ColumnType.NICKNAME)
    @WordFilter
    private String nickname;

    private String myUniversity;

    private Set<University> university;

    @Email
    @DataMatch(message = "이미 존재하는 이메일 입니다.", column = ColumnType.EMAIL)
    private String email = null;

    private String[] urlList;

    private UserRole role = UserRole.USER;

    public Account toEntity() {
        return Account
                .builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .myUniversity(myUniversity)
                .email(email)
                .urlList(urlList)
                .role(role)
                .build();
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
