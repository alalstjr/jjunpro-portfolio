package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import com.backend.project.dto.AccountPwdUpdateDTO;
import com.backend.project.dto.AccountSaveDTO;
import com.backend.project.dto.AccountUpdateDTO;
import com.backend.project.projection.AccountPublic;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.security.token.PostAuthorizationToken;
import com.backend.project.service.AccountService;
import com.backend.project.service.FileStorageService;
import com.backend.project.util.AccountUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequiredArgsConstructor
public class AccountController {
    private final AccountService        accountService;
    private final FileStorageService    fileStorageService;
    private final WebProcessRespone     webProcessRespone;
    private final AccountUtill          accountUtill;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * INSERT Account DATA
     */
    @PostMapping("")
    public ResponseEntity<Boolean> insertAccount(
            @Valid
            @RequestBody
                    AccountSaveDTO dto, BindingResult bindingResult
    ) {
        Map<String, String> errorMap  = new HashMap<String, String>();
        String              errorType = null;
        String              errorText = null;

        // Field Check
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(
                        error.getField(),
                        error.getDefaultMessage()
                );
            }
        }

        // 유효성 검사 최종 반환
        if (errorMap.size() > 0) {
            return webProcessRespone.webErrorRespone(errorMap);
        }

        Account newAccount = accountService.save(dto);
        Boolean result     = ( newAccount.getId() != null ) ? true : false;

        return new ResponseEntity<Boolean>(
                result,
                HttpStatus.CREATED
        );
    }

    /**
     * UPDATE Account DATA
     */
    @PostMapping("/{id}")
    public ResponseEntity<Long> updateAccount(
            @Valid
            @ModelAttribute
                    AccountUpdateDTO dto, BindingResult bindingResult, Authentication authentication
    ) {
        Map<String, String> errorMap  = new HashMap<String, String>();
        String              errorType = null;
        String              errorText = null;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        // Field Check
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(
                        error.getField(),
                        error.getDefaultMessage()
                );
            }
        }

        // Dto and token Check
        if (dto.getId() != accountData
                .get()
                .getId()) {
            errorType = "id";
            errorText = "접근하는 id 가 맞지 않습니다.";
            errorMap.put(
                    errorType,
                    errorText
            );
        }

        String accountEmailCheck = accountData
                .get()
                .getEmail() == null ? "" : accountData
                .get()
                .getEmail();

        // 유효성 검사 최종 반환
        if (errorMap.size() > 0) {
            return webProcessRespone.webErrorRespone(errorMap);
        }

        // 첨부파일이 존재하는 경우 파일 업로드 메소드
        if (dto.getFile() != null) {
            File fileData = fileStorageService.uploadFile(
                    dto.getFile(),
                    "account"
            );
            dto.setFileData(fileData);
        }

        Long newAccount = accountService.update(dto);

        if (newAccount == 1) {
            return new ResponseEntity<Long>(
                    newAccount,
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<Long>(
                    newAccount,
                    HttpStatus.ACCEPTED
            );
        }
    }

    /**
     * UPDATE AccountPassword DATA accountId
     */
    @PostMapping("/password/{id}")
    public ResponseEntity<Long> accountPwdUpdate(
            @Valid
            @RequestBody
                    AccountPwdUpdateDTO dto, BindingResult bindingResult, Authentication authentication
    ) {
        Map<String, String> errorMap  = new HashMap<String, String>();
        String              errorType = null;
        String              errorText = null;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        // Account Password Encode
        String rawPassword = dto.getOldPassword();
        String oldPassword = accountData
                .get()
                .getPassword();
        Boolean passwordMatches = passwordEncoder.matches(
                rawPassword,
                oldPassword
        );

        // Field Check
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(
                        error.getField(),
                        error.getDefaultMessage()
                );
            }
        }

        // Account OldPassword DB Check
        if (!passwordMatches) {
            errorType = "oldPassword";
            errorText = "이전 비밀번호가 같지 않습니다.";
            errorMap.put(
                    errorType,
                    errorText
            );
        }

        if (!dto
                .getPassword()
                .equals(dto.getPasswordRe())) {
            errorType = "passwordRe";
            errorText = "새로운 비밀번호가 같지 않습니다.";
            errorMap.put(
                    errorType,
                    errorText
            );
        }

        // 유효성 검사 최종 반환
        if (errorMap.size() > 0) {
            return webProcessRespone.webErrorRespone(errorMap);
        }

        // 조회하는 유저의 본인 ID값을 설정
        dto.setId(accountData
                .get()
                .getId());

        Long newAccount = accountService.pwdUpdate(dto);

        if (newAccount == 1) {
            return new ResponseEntity<Long>(
                    newAccount,
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<Long>(
                    newAccount,
                    HttpStatus.ACCEPTED
            );
        }
    }

    @GetMapping("/public")
    public List<AccountPublic> getPublicAccountList() {
        return accountService.findByPublicAccountList();
    }

    /**
     * GET Account DATA userId
     */
    @GetMapping("/public/{userId}")
    public ResponseEntity<AccountPublic> getPublicAccount(
            @PathVariable
                    String userId
    ) {
        AccountPublic accountPublic = accountService.findOnePublicAccount(userId);

        return new ResponseEntity<AccountPublic>(
                accountPublic,
                HttpStatus.OK
        );
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> adminAuthCheck(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        return new ResponseEntity<String>(
                token
                        .getPrincipal()
                        .toString(),
                HttpStatus.OK
        );
    }

    @GetMapping("/go")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUsername(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        System.out.println(authentication);
        return null;
    }
}
