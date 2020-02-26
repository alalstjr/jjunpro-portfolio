package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Store;
import com.jjunpro.project.dto.UpdateAccountPwdDTO;
import com.jjunpro.project.projection.AccountPublic;

public interface AccountRepositoryDSL {

    Long updatePassword(UpdateAccountPwdDTO dto);

    AccountPublic findOnePublicAccount(String username);

    Long updateAccountRoleSeller(Account account, Store store);
}
