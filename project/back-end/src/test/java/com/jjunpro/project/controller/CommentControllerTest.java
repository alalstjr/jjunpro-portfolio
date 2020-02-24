package com.jjunpro.project.controller;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.CreateCommentDTO;
import com.jjunpro.project.projection.CommentPublic;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.repository.CommentRepository;
import com.jjunpro.project.repository.UniversityRepository;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.service.CommentService;
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

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

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

    @Test
    public void getCommentListUniId() throws Exception {
        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();
        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 게시글을 생성합니다. */
        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);

        /* 임시 댓글을 하나 생성합니다. */
        Optional<University> universityOptional = universityRepository.findById(university.getId());
        CreateCommentDTO dto = CreateCommentDTO
                .builder()
                .account(userDetails.getAccount())
                .content("content")
                .ip("0.0.0.0")
                .uniId(university.getId())
                .university(universityOptional.get())
                .build();

        log.info("댓글 생성 확인 -> " + commentService
                .save(dto)
                .toString());

        mockMvc
                .perform(get("/comment/" + university.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andDo(print());

        log.info("댓글 목록 확인 -> " + universityRepository
                .findById(university.getId())
                .get()
                .getComments()
                .size());
    }

    @Test
    public void deleteCommentId() throws Exception {
        /* 일반 유저를 생성합니다. */
        accountUtil.setAccount();
        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 게시글을 생성합니다. */
        UniversityPublic university = universityUtil.getUniversityPublic(userDetails);

        /* 임시 댓글을 하나 생성합니다. */
        Optional<University> universityOptional = universityRepository.findById(university.getId());
        CreateCommentDTO dto = CreateCommentDTO
                .builder()
                .account(userDetails.getAccount())
                .content("content")
                .ip("0.0.0.0")
                .uniId(university.getId())
                .university(universityOptional.get())
                .build();

        CommentPublic save = commentService.save(dto);

        log.info("댓글 생성 확인 -> " + save.toString());

        String accessToken = accountUtil.getJwtoken();

        mockMvc
                .perform(delete("/comment/" + save.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("success")))
                .andDo(print());

        log.info("게시물에 등록된 댓글 목록 확인 -> " + universityRepository
                .findById(university.getId())
                .get()
                .getComments()
                .size());

        if(commentRepository.findById(save.getId()).isPresent()) {
            log.info("댓글이 삭제되지 않았습니다.");
        }
    }
}