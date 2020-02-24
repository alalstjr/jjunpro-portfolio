package com.jjunpro.project.controller;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.File;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountPwdDTO;
import com.jjunpro.project.projection.AccountPublic;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService     accountService;
    private final FileStorageService fileStorageService;

    /**
     * INSERT Account DATA
     */
    @PostMapping("")
    public ResponseEntity<?> createAccount(
            @Valid
            @RequestBody
                    CreateAccountDTO dto,
            BindingResult bindingResult
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        /* 새로운 유저의 정보를 DB 에 저장합니다. */
        Account account = accountService.createAccount(dto);

        return new ResponseEntity<>(
                account.getId() != null,
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
                    UpdateAccountDTO dto,
            BindingResult bindingResult
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        /* 유저의 정보를 DB 에 저장합니다. */
        Account account = accountService.updateAccount(dto);

        this.fileUpload(dto);

        /* 업데이트 되는 유저의 정보를 DB 에 저장합니다. */
        return new ResponseEntity<>(
                account.getId() != null,
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
                    UpdateAccountPwdDTO dto, BindingResult bindingResult
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return new ResponseEntity<>(
                accountService.updatePassword(dto),
                HttpStatus.OK
        );
    }

    /**
     * GET Account DATA userId
     */
    @GetMapping("/{username}")
    public ResponseEntity<AccountPublic> getPublicAccount(
            @PathVariable
                    String username
    ) {
        AccountPublic accountPublic = accountService.findOnePublicAccount(username);

        return new ResponseEntity<>(
                accountPublic,
                HttpStatus.OK
        );
    }

    @GetMapping("/check")
    public String check() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        System.out.println(authentication.getPrincipal());

        return "check";
    }

    private void fileUpload(UpdateAccountDTO dto) {
        /* 파일을 전송받는경우와 안받는 경우 */
        if (dto
                .toEntity()
                .getPhoto() != null) {
            /* Account Info */
            Optional<Account> accountData = accountService.findById(dto
                    .toEntity()
                    .getId());

            if (accountData.isPresent()) {
                File accountFile = accountData
                        .get()
                        .getPhoto();

                /* 기존에 업로드된 파일이 존재할 경우 */
                if (accountFile != null) {
                    /* Account 저장된 File 삭제 */
                    fileStorageService.fileDelete(accountFile);
                }
            }
        }

        /* 첨부파일이 존재하는 경우 파일 업로드 메소드입니다. */
        if (dto.getFile() != null && !dto
                .getFile()
                .isEmpty()) {
            File fileData = fileStorageService.uploadFile(
                    dto.getFile(),
                    "account"
            );
            dto.setFileData(fileData);
        }
    }
}
