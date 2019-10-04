package com.backend.koreanair.controller;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.dto.AccountSaveDTO;
import com.backend.koreanair.projection.AccountPublic;
import com.backend.koreanair.respone.WebProcessRespone;
import com.backend.koreanair.security.token.PostAuthorizationToken;
import com.backend.koreanair.service.AccountServiceImpl;
import com.backend.koreanair.util.ValidityCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private WebProcessRespone webProcessRespone;

    @Autowired
    private ValidityCheck validityCheck;

    @PostMapping("")
    public ResponseEntity<?> saveOrUpdate(
            @Valid
            @RequestBody AccountSaveDTO dto
    ) {
        String errorType = null;
        String errorText = null;

        // PasswordRe Check
        if(dto.getPasswordRe() == null) {
            errorType = "AuthenticationError";
            errorText = "패스워드 확인을 입력해주세요.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        // Password equals PasswordRe Check
        if(!dto.toEntity().getPassword().equals(dto.getPasswordRe())) {
            errorType = "AuthenticationError";
            errorText = "비밀번호가 일치하지 않습니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        // Account Id DB Check
        if(accountService.findByUserId(dto.getUserId()).isPresent()) {
            errorType = "AuthenticationError";
            errorText = "이미 존재하는 아이디입니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        // Account Nickname DB Check
        if(accountService.findByNickname(dto.getNickname()).isPresent()) {
            errorType = "AuthenticationError";
            errorText = "이미 존재하는 넥네임입니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        // Account Email Validity Check
        if(!validityCheck.emailCheck(dto.getEmail())) {
            errorType = "AuthenticationError";
            errorText = "올바르지 않은 이메일입니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        Account newAccount = accountService.saveOrUpdate(dto);
        return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping("")
    public Page<AccountPublic> getPublicAccountList(
            Pageable pageable
    ) {
        return accountService.findByPublicAccountList(pageable);
    }

    @GetMapping("/{id}")
    @PostAuthorize("isAnonymous()")
    public ResponseEntity<AccountPublic> getPublicAccount(
            @PathVariable Long id
    ) {
        AccountPublic accountPublic = accountService.findOnePublicAccount(id);

        return new ResponseEntity<AccountPublic>(accountPublic, HttpStatus.OK);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> adminAuthCheck(
            Authentication authentication
    ) {
        PostAuthorizationToken token = (PostAuthorizationToken)authentication;
        return new ResponseEntity<String>(token.getPrincipal().toString(), HttpStatus.OK);
    }

    @GetMapping("/go")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUsername(
            Authentication authentication
    ) {
        PostAuthorizationToken token = (PostAuthorizationToken)authentication;
        System.out.println(authentication);
        return null;
    }
}
