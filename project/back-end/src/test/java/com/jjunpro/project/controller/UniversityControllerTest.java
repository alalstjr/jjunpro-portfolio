package com.jjunpro.project.controller;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.repository.UniversityRepository;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.service.UniversityService;
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

import javax.transaction.Transactional;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UniversityControllerTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UniversityService universityService;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountUtilTest accountUtil;

    @Autowired
    UniversityUtilTest universityUtil;

    @Test
    @Transactional
    public void insertUniversity() throws Exception {
        String accessToken = accountUtil.getJwtoken();
        accountUtil.setAccount();

        mockMvc
                .perform(post("/university")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                        .param(
                                "uniSubject",
                                "uniSubject"
                        )
                        .param(
                                "uniContent",
                                "uniContent"
                        )
                        .param(
                                "uniName",
                                "uniName"
                        )
                        .param(
                                "stoId",
                                "stoId"
                        )
                        .param(
                                "stoName",
                                "stoName"
                        )
                        .param(
                                "stoAddress",
                                "stoAddress"
                        )
                        .param(
                                "stoUrl",
                                "stoUrl"
                        ))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Transactional
    public void updateUniversity() throws Exception {
        String accessToken = accountUtil.getJwtoken();

        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        /* 다른 유저를 생성합니다. */
        accountUtil.setAccount(
                "jjunpro",
                "jjunnick",
                "por@naver.com"
        );

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");
        log.info("일반 유저 -> " + userDetails
                .getAccount()
                .toString());

        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);

        mockMvc
                .perform(post("/university/" + university
                        .getId()
                        .toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                        .param(
                                "id",
                                university
                                        .getId()
                                        .toString()
                        )
                        .param(
                                "uniSubject",
                                "uniSubject"
                        )
                        .param(
                                "uniContent",
                                "uniContent"
                        )
                        .param(
                                "uniName",
                                "uniName"
                        ))
                .andExpect(status().isCreated())
                .andDo(print());

        Optional<University> byId = universityRepository.findById(university.getId());
        log.info("수정된 university -> " + byId
                .get()
                .toString());
    }

    @Test
    @Transactional
    void UpdateUniLikeUniId() throws Exception {
        String accessToken = accountUtil.getJwtoken();

        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");
        log.info("일반 유저 -> " + userDetails
                .getAccount()
                .toString());

        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);
        log.info("좋아요를 체크 전 university -> " + university
                .getUniLike()
                .toString());

        mockMvc
                .perform(post("/university/like/" + university.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        ))
                .andExpect(status().isOk())
                .andDo(print());

        /* 좋아요를 체크 후 university */
        Optional<University> byId = universityRepository.findById(university.getId());
        log.info("좋아요를 체크 후 university -> " + byId
                .get()
                .getUniLike()
                .toString());
    }

    @Test
    @Transactional
    public void getUniList() throws Exception {
        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);
        log.info("게시글 작성 확인 -> " + universityService
                .findById(university.getId())
                .get()
                .toString());

        mockMvc
                .perform(get("/university/search")
                        .param(
                                "category",
                                "nickname"
                        )
                        .param(
                                "keyword",
                                "nickname"
                        )
                        .param(
                                "offsetCount",
                                "0"
                        )
                        .param(
                                "ifCateA",
                                "all"
                        )
                        .param(
                                "ifCateB",
                                "all"
                        ))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void getUniversityCreatedDate() throws Exception {
        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 게시글 2개를 생성합니다. */
        universityUtil.getUniversityPublic(userDetails);
        universityUtil.getUniversityPublic(userDetails);

        mockMvc
                .perform(get("/university"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andDo(print());
    }

    @Test
    @Transactional
    public void getUniversityUniId() throws Exception {
        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 게시글을 생성합니다. */
        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);

        mockMvc
                .perform(get("/university/" + university.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andDo(print());
    }

    @Test
    @Transactional
    public void getUniCountUniId() throws Exception {
        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 게시글을 생성합니다. */
        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);

        mockMvc
                .perform(get("/university/count/" + university.getUniName()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("uniName")))
                .andDo(print());
    }

    @Test
    @Transactional
    public void deleteUniversityuniId() throws Exception {
        String accessToken = accountUtil.getJwtoken();

        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 게시글을 생성합니다. */
        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);

        mockMvc
                .perform(delete("/university/" + university.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("success")))
                .andDo(print());

        if(universityRepository.findById(university.getId()).isEmpty()) {
            log.info("임시 게시글 삭제완료");
        }
    }
}