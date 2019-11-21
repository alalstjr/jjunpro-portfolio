package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.dto.AccountPwdUpdateDTO;
import com.backend.project.dto.AccountSaveDTO;
import com.backend.project.dto.AccountUpdateDTO;
import com.backend.project.projection.AccountPublic;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    public Optional<Account> findByUserId(String userId);
    public Optional<Account> findByNickname(String nickname);
    public Optional<Account> findByEmail(String email);

    public AccountPublic findOnePublicAccount(String userId);
    public List<AccountPublic> findByPublicAccountList();

    public Account save(AccountSaveDTO dto);
    public Long update(AccountUpdateDTO dto);
    public Long pwdUpdate(AccountPwdUpdateDTO dto);
}
