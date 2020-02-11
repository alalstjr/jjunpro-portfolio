package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.service.StoreService;
import com.backend.project.util.AccountUtill;
import com.backend.project.util.SearchUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final AccountUtill accountUtill;
    private final SearchUtill  searchUtill;

    /**
     * GET University List DATA StoreId
     */
    @GetMapping("/{keyword}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getStoreIdUniList(
            @PathVariable
                    String keyword,
            @RequestParam("classification")
                    String classification,
            @RequestParam("offsetCount")
                    int offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        SearchDTO searchDTO = searchUtill.setSearchDTO(
                keyword,
                classification,
                offsetCount,
                ifCateA,
                ifCateB,
                accountData
        );

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
    @PreAuthorize("permitAll()")
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
