package com.jjunpro.project.controller;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.UniversityDTO;
import com.jjunpro.project.repository.UniversityRepository;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.service.UniversityService;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Test
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
                        ))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void updateUniversity() throws Exception {
        String accessToken = accountUtil.getJwtoken();

        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();

        /* 다른 유저를 생성합니다. */
        accountUtil.setAccount("jjunpro","jjunnick", "por@naver.com");

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");
        log.info("get default user -> " + userDetails.getAccount().toString());

        /* 임시 게시글을 하나 생성합니다. */
        UniversityDTO dto = new UniversityDTO();
        dto.setAccount(userDetails.getAccount());
        dto.setUniSubject("subject");
        dto.setUniContent("content");
        dto.setUniName("uniName");
        dto.setUniIp("0.0.0.0");
        University university = universityService.createUniversity(dto);
        log.info("get default university -> " + university.getAccount());

        mockMvc
                .perform(post("/university/" + university.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                        .param(
                                "id",
                                university.getId().toString()
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
                .andExpect(status().isOk())
                .andDo(print());

        Optional<University> byId = universityRepository.findById(university.getId());
        log.info("get update university -> " + byId.get().toString());

    }
}