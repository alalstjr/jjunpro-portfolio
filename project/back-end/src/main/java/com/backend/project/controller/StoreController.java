package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.service.StoreServiceImpl;
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
public class StoreController {

    @Autowired
    private StoreServiceImpl storeService;

    @Autowired
    private AccountUtill accountUtill;

    // GET LIST
    /**
     *  storeId 상점 고유번호 & offsetCount 페이지 번호
     */
    @GetMapping("/{storeId}/{offsetCount}")
    @PreAuthorize("permitAll()")
    public List<UniversityPublic> getStoreIdUniList(
            @PathVariable String storeId,
            @PathVariable Long offsetCount,
            HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        return storeService.findByStoreUniAll(storeId, accountData, offsetCount);
    }

    @GetMapping("/{storeId}/count")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Long>> getStoreIdCount(
            @PathVariable String storeId
    ) {
        Long uniCountData = storeService.findByUniCount(storeId);

        Map<String, Long> resMap = new HashMap<String, Long>();
        resMap.put("count", uniCountData);

        return new ResponseEntity<Map<String, Long>>(resMap, HttpStatus.OK);
    }
}
