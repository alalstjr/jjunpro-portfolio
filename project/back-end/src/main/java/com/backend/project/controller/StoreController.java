package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.service.StoreService;
import com.backend.project.util.AccountUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/store")
@CrossOrigin
public class StoreController
{

    @Autowired
    private StoreService storeService;

    @Autowired
    private AccountUtill accountUtill;

    /**
     *  GET University List DATA StoreId
     */
    @GetMapping("/{keyword}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getStoreIdUniList(
            @PathVariable String keyword,
            @RequestParam("classification") String classification,
            @RequestParam("offsetCount") Long offsetCount,
            @RequestParam("ifCateA") String ifCateA,
            @RequestParam("ifCateB") String ifCateB,
            HttpServletRequest request
    ) throws IOException
    {

        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        if(keyword != null || offsetCount != null)
        {
            // Param 값을 DTO 에 담아줍니다.
            // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
            searchDTO.setKeyword(keyword);
            searchDTO.setClassification(classification);
            searchDTO.setOffsetCount(offsetCount);
            searchDTO.setAccount(accountData);
            searchDTO.setIfCateA(ifCateA);
            searchDTO.setIfCateB(ifCateB);
        }
        else
        {
            throw new IOException("검색에 필요한 정보를 전부 받지 못했습니다.");
        }

        List<UniversityPublic> result = storeService.findByStoreUniAll(searchDTO);

        return new ResponseEntity<List<UniversityPublic>>(result, HttpStatus.OK);
    }

    /**
     *  GET University Count DATA StoreId
     */
    @GetMapping("/count/{keyword}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Long>> getUniCountStoId(
            @PathVariable String keyword
    )
    {
        Long result = storeService.findByUniCount(keyword);

        Map<String, Long> resultMap = new HashMap<String, Long>();
        resultMap.put("count", result);

        return new ResponseEntity<Map<String, Long>>(resultMap, HttpStatus.OK);
    }
}
