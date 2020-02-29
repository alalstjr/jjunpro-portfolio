package com.jjunpro.project.util;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.enums.UserRole;
import com.jjunpro.project.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AccountUtilTest {

    private final String JWTOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJST0xFIjoiVVNFUiIsIk5JQ0tOQU1FIjoibmlja25hbWUiLCJpc3MiOiJqanVucHJvIiwiVVNFUk5BTUUiOiJ1c2VybmFtZSIsIkVYUCI6MTU4MzE3MDAwNX0.VKRR-39AqUqedp6JZgYH0bguSN6vyzStShxKt_kRoeA";

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    /* 임시의 새로운 기본 유저를 생성 등록합니다. */
    public void setAccount() {
        CreateAccountDTO dto = new CreateAccountDTO();
        dto.setUsername("username");
        dto.setNickname("nickname");
        dto.setEmail("jjun-pro@naver.com");
        dto.setPassword("1234");
        dto.setPasswordRe("1234");
        dto.setRole(UserRole.USER);
        dto.encodePassword(passwordEncoder);
        Account save = accountRepository.save(dto.toEntity());

        log.info(save.toString());
    }

    /* 임시의 새로운 설정된 유저를 생성 등록합니다. */
    public void setAccount(String username, String nickname, String email) {
        CreateAccountDTO dto = new CreateAccountDTO();
        dto.setUsername(username);
        dto.setNickname(nickname);
        dto.setEmail(email);
        dto.setPassword("1234");
        dto.setPasswordRe("1234");
        dto.setRole(UserRole.USER);
        dto.encodePassword(passwordEncoder);
        Account save = accountRepository.save(dto.toEntity());

        log.info(save.toString());
    }

    public String getJwtoken() {
        return JWTOKEN;
    }
}
