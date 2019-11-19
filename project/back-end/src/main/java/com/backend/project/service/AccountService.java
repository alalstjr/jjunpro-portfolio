package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.dto.AccountSaveDTO;
import com.backend.project.dto.AccountUpdateDTO;
import com.backend.project.projection.AccountPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {
    public Optional<Account> findByUserId(String userId);
    public Optional<Account> findByNickname(String nickname);
    public Optional<Account> findByEmail(String email);

    public AccountPublic findOnePublicAccount(String userId);
    public Page<AccountPublic> findByPublicAccountList(Pageable pageable);

    public Account save(AccountSaveDTO dto);
    public Long update(AccountUpdateDTO dto);
}
