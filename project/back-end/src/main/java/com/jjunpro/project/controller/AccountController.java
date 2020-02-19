package com.jjunpro.project.controller;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountDTO;
import com.jjunpro.project.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

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
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        // 새로운 유저의 정보를 DB 에 저장합니다.
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
            @RequestBody
                    UpdateAccountDTO dto,
            BindingResult bindingResult
    ) throws BindException {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        // 새로운 유저의 정보를 DB 에 저장합니다.
        Account account = accountService.updateAccount(dto);

        // 업데이트 되는 유저의 정보를 DB 에 저장합니다.
        return new ResponseEntity<>(
                account.getId() != null,
                HttpStatus.OK
        );
    }
}
