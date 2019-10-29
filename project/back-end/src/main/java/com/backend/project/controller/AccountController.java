package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.dto.AccountSaveDTO;
import com.backend.project.projection.AccountPublic;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.security.token.PostAuthorizationToken;
import com.backend.project.service.AccountServiceImpl;
import com.backend.project.util.ValidityCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private WebProcessRespone webProcessRespone;

    @Autowired
    private ValidityCheckUtil validityCheck;

    @PostMapping("")
    public ResponseEntity<Account> saveOrUpdate(
            @Valid @RequestBody AccountSaveDTO dto,
            BindingResult bindingResult
    ) {
        Map<String, String> errorMap = new HashMap<String, String>();
        String errorType = null;
        String errorText = null;

        // Field Check
        if(bindingResult.hasErrors()) {
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
        }

        // Password equals PasswordRe Check
        if(!dto.toEntity().getPassword().equals(dto.getPasswordRe())) {
            errorType = "password";
            errorText = "비밀번호가 일치하지 않습니다.";
            errorMap.put(errorType, errorText);
        }

        // Account Id DB Check
        if(accountService.findByUserId(dto.getUserId()).isPresent()) {
            errorType = "userId";
            errorText = "이미 존재하는 아이디입니다.";
            errorMap.put(errorType, errorText);
        }

        // Account Nickname DB Check
        if(accountService.findByNickname(dto.getNickname()).isPresent()) {
            errorType = "nickname";
            errorText = "이미 존재하는 넥네임입니다.";
            errorMap.put(errorType, errorText);
        }

        // Account Email Validity Check
        if (dto.getEmail() != null && !validityCheck.emailCheck(dto.getEmail())) {
            errorType = "email";
            errorText = "올바르지 않은 이메일입니다.";
            errorMap.put(errorType, errorText);
        }

        // 유효성 검사 최종 반환
        if(errorMap.size() > 0) {
            return webProcessRespone.webErrorRespone(errorMap);
        }

//        Account newAccount = accountService.saveOrUpdate(dto);
//        return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
        return null;
    }

    @GetMapping("")
    public Page<AccountPublic> getPublicAccountList(
            Pageable pageable
    ) {
        return accountService.findByPublicAccountList(pageable);
    }

    @GetMapping("/{id}")
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
