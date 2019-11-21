package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.dto.AccountSaveDTO;
import com.backend.project.dto.AccountUpdateDTO;
import com.backend.project.projection.AccountPublic;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.security.token.PostAuthorizationToken;
import com.backend.project.service.AccountServiceImpl;
import com.backend.project.util.AccountUtill;
import com.backend.project.util.ValidityCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    private AccountUtill accountUtill;

    @PostMapping("")
    public ResponseEntity<Account> accountInsert(
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
        if (accountService.findByEmail(dto.getEmail()).isPresent()) {
            errorType = "email";
            errorText = "이미 존재하는 이메일입니다.";
            errorMap.put(errorType, errorText);
        }

        // 유효성 검사 최종 반환
        if(errorMap.size() > 0) {
            return webProcessRespone.webErrorRespone(errorMap);
        }

        Account newAccount = accountService.save(dto);
        return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> accountUpdate(
            @Valid @RequestBody AccountUpdateDTO dto,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        Map<String, String> errorMap = new HashMap<String, String>();
        String errorType = null;
        String errorText = null;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        // Field Check
        if(bindingResult.hasErrors()) {
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
        }

        // Dto and token Check
        if(dto.getId() != accountData.get().getId()) {
            errorType = "id";
            errorText = "접근하는 id 가 맞지 않습니다.";
            errorMap.put(errorType, errorText);
        }

        // Account Nickname DB Check
        if(
            !accountData.get().getNickname().equals(dto.getNickname()) &&
            accountService.findByNickname(dto.getNickname()).isPresent()
        ) {
            errorType = "nickname";
            errorText = "이미 존재하는 닉네임입니다.";
            errorMap.put(errorType, errorText);
        }

        // Account Email Validity Check
        if (dto.getEmail() != null && !validityCheck.emailCheck(dto.getEmail())) {
            errorType = "email";
            errorText = "올바르지 않은 이메일입니다.";
            errorMap.put(errorType, errorText);
        }
        if (
            !accountData.get().getEmail().equals(dto.getEmail()) &&
            accountService.findByEmail(dto.getEmail()).isPresent()
        ) {
            errorType = "email";
            errorText = "이미 존재하는 이메일입니다.";
            errorMap.put(errorType, errorText);
        }

        // 유효성 검사 최종 반환
        if(errorMap.size() > 0) {
            return webProcessRespone.webErrorRespone(errorMap);
        }

        Long newAccount = accountService.update(dto);

        if(newAccount == 1) {
            return new ResponseEntity<Long>(newAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<Long>(newAccount, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/public")
    public List<AccountPublic> getPublicAccountList() {
        return accountService.findByPublicAccountList();
    }

    @GetMapping("/public/{userId}")
    public ResponseEntity<AccountPublic> getPublicAccount(
            @PathVariable String userId
    ) {
        AccountPublic accountPublic = accountService.findOnePublicAccount(userId);

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
