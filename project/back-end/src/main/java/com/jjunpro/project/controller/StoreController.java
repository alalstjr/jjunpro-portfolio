package com.jjunpro.project.controller;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.SellerDTO;
import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.service.StoreService;
import com.jjunpro.project.util.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final AccountUtil    accountUtil;
    private final StoreService   storeService;

    /**
     * GET University List DATA StoreId
     */
    @GetMapping("")
    public ResponseEntity<?> getStoreIdUniList(
            @RequestParam
                    String keyword,
            @RequestParam("category")
                    String category,
            @RequestParam("offsetCount")
                    int offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB,
            HttpServletRequest request
    ) throws IOException {
        /* Account Info */
        Account accountData = accountUtil.accountJWT(request);

        SearchDTO searchDTO = SearchDTO
                .builder()
                .category(category)
                .keyword(keyword)
                .offsetCount(offsetCount)
                .ifCateA(ifCateA)
                .ifCateB(ifCateB)
                .accountData(accountData)
                .build();

        List<UniversityPublic> result = storeService.findByStoreUniAll(searchDTO);

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University Count DATA StoreId
     */
    @GetMapping("/count/{keyword}")
    public ResponseEntity<Map<String, Long>> getUniCountStoId(
            @PathVariable
                    String keyword
    ) {
        Long result = storeService.findByUniCount(keyword);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put(
                "count",
                result
        );

        return new ResponseEntity<>(
                resultMap,
                HttpStatus.OK
        );
    }
}
