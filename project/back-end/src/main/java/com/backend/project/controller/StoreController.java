package com.backend.project.controller;

import com.backend.project.projection.StorePublic;
import com.backend.project.service.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/store")
@CrossOrigin
public class StoreController {

    @Autowired
    private StoreServiceImpl storeService;

    // GET LIST
    @GetMapping("/{storeId}")
    @PreAuthorize("permitAll()")
    public Page<StorePublic> getStoreIdUniList(
            @PathVariable String storeId,
            Pageable pageable
    ) {
        return storeService.findByStoreUniAll(pageable, storeId);
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
