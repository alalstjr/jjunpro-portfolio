package com.backend.koreanair.service;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.dto.AccountSaveDTO;
import com.backend.koreanair.projection.AccountPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {
    public Optional<Account> findByUserId(String userId);
    public Optional<Account> findByNickname(String nickname);

    public AccountPublic findOnePublicAccount(Long id);
    public Page<AccountPublic> findByPublicAccountList(Pageable pageable);

    public Account saveOrUpdate(AccountSaveDTO dto);
}
