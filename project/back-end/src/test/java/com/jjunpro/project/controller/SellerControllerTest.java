package com.jjunpro.project.controller;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.dto.SellerDTO;
import com.jjunpro.project.repository.SellerRepository;
import com.jjunpro.project.repository.StoreRepository;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.service.StoreService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountUtilTest accountUtilTest;

    @Autowired
    AccountService accountService;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Test
    public void insertFoodMenu() throws Exception {
        /* 등록하는 가게 Id 값 */
        final String stoId = "1234";

        /* 유저를 생성합니다. */
        accountUtilTest.setAccount();
        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 가게정보를 등록합니다. */
        SellerDTO build = SellerDTO
                .builder()
                .name("test")
                .phoneNumber("010-1234-1234")
                .stoId(stoId)
                .stoName("stoName")
                .stoAddress("stoAddress")
                .stoUrl("stoUrl")
                .account(userDetails.getAccount())
                .build();
        accountService.updateAccountRoleSeller(build);

        log.info("등록된 가게정보 => " + storeRepository
                .findByStoId(stoId)
                .get()
                .toString());

        /* 가게정보에 메뉴를 추가합니다. */
        String dataJson = "{\"stoId\":" + stoId + ", \"name\":\"name\", \"content\":\"content\", \"price\":\"10000\", \"subMenus\": [ { \"name\":\"name\", \"price\":\"20000\" } ]}";

        mockMvc
                .perform(post("/seller")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(dataJson))
                .andExpect(status().isOk())
                .andDo(print());

        /* 추가한 음식 메뉴를 확인합니다. */
        log.info("등록된 음식메뉴 => " + storeRepository.findByStoId(stoId).get().getFoodMenu());
    }
}