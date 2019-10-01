package com.backend.koreanair.controller;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.dto.AccountSaveDTO;
import com.backend.koreanair.projection.AccountPublic;
import com.backend.koreanair.security.token.PostAuthorizationToken;
import com.backend.koreanair.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountContoller {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @PostMapping("")
    public ResponseEntity<?> saveOrUpdate(
            @Valid
            @RequestBody AccountSaveDTO dto
    ) {
        if(accountServiceImpl.findByUserId(dto.getUserId()).isPresent()) {
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("AuthenticationError", "이미 존재하는 아이디 입니다.");

            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        Account newAccount = accountServiceImpl.saveOrUpdate(dto);
        return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping("")
    public Page<AccountPublic> getPublicAccountList(
            Pageable pageable
    ) {
        return accountServiceImpl.findByUserPublic(pageable);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> adminAuthCheck(
            Authentication authentication
    ) {
        PostAuthorizationToken token = (PostAuthorizationToken)authentication;

        return new ResponseEntity<String>(token.getPrincipal().toString(), HttpStatus.OK);
    }

    @GetMapping("/go")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUsername(
            Authentication authentication
    ) {
        PostAuthorizationToken token = (PostAuthorizationToken)authentication;
        System.out.println(authentication);
        return null;
    }
}
