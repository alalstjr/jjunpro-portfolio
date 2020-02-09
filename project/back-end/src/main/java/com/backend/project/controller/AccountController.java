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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService     accountService;
    private final FileStorageService fileStorageService;
    private final WebProcessRespone  webProcessRespone;

    /**
     * INSERT Account DATA
     */
    @PostMapping("")
    public ResponseEntity<?> insertAccount(
            @Valid
            @RequestBody
                    AccountSaveDTO dto, BindingResult bindingResult
    ) {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            return webProcessRespone.webErrorRespone(bindingResult);
        }

        // 새로운 유저의 정보를 DB 에 저장합니다.
        Account newAccount = accountService.save(dto);

        return new ResponseEntity<>(
                newAccount.getId() != null,
                HttpStatus.CREATED
        );
    }

    /**
     * UPDATE Account DATA
     */
    @PostMapping("/{id}")
    public ResponseEntity<?> updateAccount(
            @Valid
            @ModelAttribute
                    AccountUpdateDTO dto, BindingResult bindingResult
    ) {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            return webProcessRespone.webErrorRespone(bindingResult);
        }

        // 첨부파일이 존재하는 경우 파일 업로드 메소드입니다.
        if (!dto
                .getFile()
                .isEmpty()) {
            File fileData = fileStorageService.uploadFile(
                    dto.getFile(),
                    "account"
            );
            dto.setFileData(fileData);
        }

        // 업데이트 되는 유저의 정보를 DB 에 저장합니다.
        return new ResponseEntity<>(
                accountService.update(dto),
                HttpStatus.OK
        );
    }

    /**
     * UPDATE AccountPassword DATA accountId
     */
    @PostMapping("/password/{id}")
    public ResponseEntity<?> accountPwdUpdate(
            @Valid
            @RequestBody
                    AccountPwdUpdateDTO dto, BindingResult bindingResult
    ) {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            return webProcessRespone.webErrorRespone(bindingResult);
        }

        return new ResponseEntity<>(
                accountService.pwdUpdate(dto),
                HttpStatus.OK
        );
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

        return new ResponseEntity<>(
                accountPublic,
                HttpStatus.OK
        );
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> adminAuthCheck(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;

        return new ResponseEntity<>(
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

