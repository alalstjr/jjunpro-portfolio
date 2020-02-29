package com.jjunpro.project.controller;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Store;
import com.jjunpro.project.dto.FoodMenuDTO;
import com.jjunpro.project.dto.SellerDTO;
import com.jjunpro.project.repository.SellerRepository;
import com.jjunpro.project.repository.StoreRepository;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.service.SellerService;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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

    @Autowired
    SellerService sellerService;

    @Autowired
    AccountUtilTest accountUtil;

    /* 등록하는 가게 Id 값 */
    final String _stoId = "1234";

    //@Test
    public void insertFoodMenu() throws Exception {
        /* 유저를 생성합니다. */
        accountUtilTest.setAccount();
        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        this.setStore(userDetails);

        /* 가게정보에 메뉴를 추가합니다. */
        String dataJson = "{\"stoId\":" + _stoId + ", \"name\":\"name\", \"content\":\"content\", \"price\":\"10000\", \"subMenus\": [ { \"name\":\"name\", \"price\":\"20000\" } ]}";

        mockMvc
                .perform(post("/seller")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(dataJson))
                .andExpect(status().isOk())
                .andDo(print());

        /* 추가한 음식 메뉴를 확인합니다. */
        log.info("등록된 음식메뉴 => " + storeRepository
                .findByStoId(_stoId)
                .get()
                .getFoodMenu());
    }

    // @Test
    public void deleteFoodMenu() throws Exception {
        String accessToken = accountUtil.getJwtoken();

        /* 유저를 생성합니다. */
        accountUtilTest.setAccount();
        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername("username");

        /* 다른 유저를 생성합니다. 다른유저의 접근이 허용이 안되는지 확인하는 코드 */
        accountUtil.setAccount(
                "jjunpro",
                "jjunnick",
                "por@naver.com"
        );
        AccountContext userDetailsTwo = (AccountContext) accountService.loadUserByUsername("jjunpro");

        Optional<Store> store = this.setStore(userDetails);

        /* 메뉴를 등록합니다. */
        FoodMenuDTO foodMenuDTO = FoodMenuDTO
                .builder()
                .content("content")
                .name("name")
                .price("1234")
                .stoId(_stoId)
                .photo(null)
                .fileData(null)
                .subMenus(null)
                .store(store.get())
                .build();

        Long foodIdData = sellerService.insertFoodMenu(foodMenuDTO);

        /* 추가한 음식 메뉴를 확인합니다. */
        log.info("등록된 음식메뉴 => " + storeRepository
                .findByStoId(_stoId)
                .get()
                .getFoodMenu());

        /* 추가한 음식을 삭제합니다. */
        mockMvc
                .perform(delete("/seller/" + foodIdData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        ))
                .andExpect(status().isCreated())
                .andDo(print());

        /* 삭제된 음식 메뉴를 확인합니다. */
        log.info("등록된 음식메뉴 => " + storeRepository
                .findByStoId(_stoId)
                .get()
                .getFoodMenu());
    }

    /* 가게정보를 등록합니다. */
    private Optional<Store> setStore(
            AccountContext userDetails
    ) {
        SellerDTO build = SellerDTO
                .builder()
                .name("test")
                .phoneNumber("010-1234-1234")
                .stoId(_stoId)
                .stoName("stoName")
                .stoAddress("stoAddress")
                .stoUrl("stoUrl")
                .account(userDetails.getAccount())
                .build();
        accountService.updateAccountRoleSeller(build);

        log.info("등록된 가게정보 => " + storeRepository
                .findByStoId(_stoId)
                .get()
                .toString());

        return storeRepository.findByStoId(_stoId);
    }

}