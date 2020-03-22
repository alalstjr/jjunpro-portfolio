package com.jjunpro.project.controller;

import com.jjunpro.project.aspect.BindValidator;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.File;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.dto.SellerDTO;
import com.jjunpro.project.dto.UpdateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountPwdDTO;
import com.jjunpro.project.projection.AccountPublic;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.service.FileStorageService;
import com.jjunpro.project.util.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
// @RequestMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUtil        accountUtil;
    private final AccountService     accountService;
    private final FileStorageService fileStorageService;

    /**
     * INSERT Account DATA
     */
    @BindValidator
    @PostMapping("")
    public ResponseEntity<?> createAccount(
            @Valid
            @RequestBody CreateAccountDTO dto,
            BindingResult bindingResult
    ) {
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
    @BindValidator
    @PostMapping("/{id}")
    public ResponseEntity<?> updateAccount(
            @Valid
            @ModelAttribute
                    UpdateAccountDTO dto,
            BindingResult bindingResult
    ) {
        /*
         * Account File Upload 과정설명 (PreFile = 이전파일, NewFile = 새로운파일)
         * 1. NewFile DB 등록
         * 2. Account DB 조회 PreFile 변수에 임시 저장
         * 3. Account File 에 NewFile 저장
         * 4. PreFile 변수로 파일 삭제
         *
         * 결론으 Account Table 내부에 PreFile 정보가 들어있으면 PreFile 삭제가 안되므로 NewFile 등록 후 삭제해야 합니다.
         * NewFile 등록 이전에 PreFile 정보를 먼저 변수에 따로 저장해야 합니다.
         * */
        File prPhoto = null;

        /* 새로 등록되는 파일 */
        this.fileUpload(dto);

        /* 이전에 업로드한 삭제 예정된 파일  */
        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            Optional<Account> accountData = accountService.findById(dto.getId());
            if (accountData.isPresent()) {
                prPhoto = accountData
                        .get()
                        .getPhoto();
            }
        }

        /* 유저의 정보를 DB 에 저장합니다. */
        Account account = accountService.updateAccount(dto);

        /* 이전의 파일을 삭제합니다. */
        if (prPhoto != null) {
            fileStorageService.fileDelete(prPhoto);
        }

        /* 업데이트 되는 유저의 정보를 DB 에 저장합니다. */
        return new ResponseEntity<>(
                account != null,
                HttpStatus.OK
        );
    }

    /**
     * UPDATE AccountPassword DATA accountId
     */
    @BindValidator
    @PostMapping("/password/{id}")
    public ResponseEntity<?> accountPwdUpdate(
            @Valid
            @RequestBody
                    UpdateAccountPwdDTO dto,
            BindingResult bindingResult
    ) {
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
        return new ResponseEntity<>(
                accountService.findOnePublicAccount(username),
                HttpStatus.OK
        );
    }

    @BindValidator
    @PostMapping("/seller")
    public ResponseEntity<Long> applySeller(
            @Valid
            @RequestBody
                    SellerDTO dto,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        Optional<Account> account = accountUtil.accountInfo(authentication);
        account.ifPresent(dto::setAccount);

        /* TEST 관리자의 체크없이 바로 권한 변경가능합니다. */
        return new ResponseEntity<>(
                accountService.updateAccountRoleSeller(dto),
                HttpStatus.OK
        );
    }

    private void fileUpload(UpdateAccountDTO dto) {
        /* 파일을 전송받는경우와 안받는 경우 */
        if (dto.getFile() != null) {
            File fileData = fileStorageService.uploadFile(
                    dto.getFile(),
                    "account"
            );

            /* File <=> Account 양방향 관계설정 */
            dto.setFileData(fileData);
        }
    }
}
