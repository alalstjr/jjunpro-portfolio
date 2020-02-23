package com.jjunpro.project.controller;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.repository.UniversityRepository;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.util.AccountUtilTest;
import com.jjunpro.project.util.UniversityUtilTest;
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

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountUtilTest accountUtil;

    @Autowired
    UniversityUtilTest universityUtil;

    @Test
    public void insertComment() throws Exception {
        String accessToken = accountUtil.getJwtoken();

        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 게시글을 생성합니다. */
        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);

        String userJson = "{\"uniId\":" + university.getId() + ", \"content\":\"content\"}";

        mockMvc
                .perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                        .content(userJson))
                .andExpect(status().isCreated())
                .andDo(print());

        log.info("댓글 생성 확인 -> " + universityRepository
                .findById(university.getId())
                .get()
                .getComments()
                .size());
    }
}