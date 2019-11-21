package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.dto.AccountPwdUpdateDTO;
import com.backend.project.projection.AccountPublic;

import java.util.List;

public interface AccountRepositoryDSL {
    Long update(Account dto);
    Long pwdUpdate(AccountPwdUpdateDTO dto);

    List<AccountPublic> findByPublicAccountList();

    AccountPublic findOnePublicAccount(String userId);
}
