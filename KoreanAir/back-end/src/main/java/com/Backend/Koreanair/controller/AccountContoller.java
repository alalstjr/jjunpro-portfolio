package com.backend.koreanair.controller;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.dto.AccountSaveDTO;
import com.backend.koreanair.projection.AccountPublic;
import com.backend.koreanair.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountContoller {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @PostMapping("")
    public ResponseEntity<?> saveOrUpdate(
            @Valid
            @RequestBody AccountSaveDTO dto
    ) {
        Account newAccount = accountServiceImpl.saveOrUpdate(dto);

        System.out.println("=======dto-----");

        return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping("")
    public Iterable<AccountPublic> getPublicAccountList() {
        System.out.println("=======get-----");
        return accountServiceImpl.findByUserPublic();
    }
}
