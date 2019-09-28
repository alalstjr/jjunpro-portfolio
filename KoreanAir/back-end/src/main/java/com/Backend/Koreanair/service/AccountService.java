package com.backend.koreanair.service;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.dto.AccountSaveDTO;
import com.backend.koreanair.projection.AccountPublic;

public interface AccountService {
    public Iterable<AccountPublic> findByUserPublic();
    public Account saveOrUpdate(AccountSaveDTO dto);
}
