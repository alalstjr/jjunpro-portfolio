package com.jjunpro.project.controller;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.util.AccountUtilTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    AccountUtilTest accountUtil;

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

        accountUtil.setAccount();

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
        accountUtil.setAccount();

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

        accountUtil.setAccount();

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
        String accessToken = accountUtil.getJwtoken();

        accountUtil.setAccount();

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

    @Test
    public void accountPwdUpdate() throws Exception {
        String accessToken = accountUtil.getJwtoken();

        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");
        String userId = userDetails
                .getAccount()
                .getId()
                .toString();

        String userJson = "{\"id\":" + userId + ", \"password\":\"update\", \"passwordRe\":\"update\", \"oldPassword\":\"1234\"}";

        mockMvc
                .perform(post("/account/password/" + userDetails
                        .getAccount()
                        .getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
    }

    @Test
    public void getPublicAccount() throws Exception {
        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        mockMvc
                .perform(get("/account/" + userDetails.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(userDetails.getUsername())))
                .andDo(print());
    }
}