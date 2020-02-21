package com.jjunpro.project.controller;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.enums.UserRole;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void createAccount() throws Exception {
        String userJson = "{\"username\":\"username\", \"nickname\":\"nickname\", \"password\":\"1234\", \"passwordRe\":\"1234\"}";

        mockMvc
                .perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void updateAccount() throws Exception {
        /* 새로 생성한 유저의 정보를 수정 합니다. */
        String userJson = "{\"id\":\"1\", \"nickname\":\"username\", \"email\":\"alalstjr@naver.com\"}";

        setAccount();

        mockMvc
                .perform(post("/account/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .with(csrf())
                        .with(user("username")
                                .password("1234")
                                .roles("USER")))
                .andExpect(status().isOk())
                .andDo(print());

        /* 정보를 수정한 유저의 값을 조회합니다. 검증 */
        Optional<Account> byId = accountRepository.findById(1L);
        byId.ifPresent(account -> log.info(account.toString()));
    }

    @Test
    public void loginForm() throws Exception {
        setAccount();

        mockMvc
                .perform(post("/signin")
                        .param(
                                "username",
                                "username"
                        )
                        .param(
                                "password",
                                "1234"
                        )
                        .with(csrf()))
                .andDo(print());
    }

    @Test
    public void jwtLoginForm() throws Exception {
        String userJson = "{\"username\":\"username\", \"password\":\"1234\"}";

        setAccount();

        mockMvc
                .perform(post("/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void check() throws Exception {
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJST0xFIjoiVVNFUiIsIk5JQ0tOQU1FIjoibmlja25hbWUiLCJpc3MiOiJqanVucHJvIiwiVVNFUk5BTUUiOiJ1c2VybmFtZSIsIkVYUCI6MTU4MzE3MDAwNX0.VKRR-39AqUqedp6JZgYH0bguSN6vyzStShxKt_kRoeA";

        setAccount();

        mockMvc
                .perform(get("/account/check")
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /* 새로운 유저를 생성 등록합니다. */
    private void setAccount() {
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
}