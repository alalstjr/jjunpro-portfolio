package com.jjunpro.project.repository;

import com.jjunpro.project.dto.UpdateAccountPwdDTO;
import com.jjunpro.project.projection.AccountPublic;

public interface AccountRepositoryDSL {

    Long updatePassword(UpdateAccountPwdDTO dto);

    AccountPublic findOnePublicAccount(String username);
}
