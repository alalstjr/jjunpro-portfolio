package com.backend.koreanair.service;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.dto.AccountSaveDTO;
import com.backend.koreanair.projection.AccountPublic;

import java.util.Optional;

public interface AccountService {
    public Iterable<AccountPublic> findByUserPublic();
    public Optional<Account> findByUserId(String userId);
    public Account saveOrUpdate(AccountSaveDTO dto);
}
