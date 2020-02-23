package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountPwdDTO;
import com.jjunpro.project.projection.AccountPublic;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface AccountService {

    Account createAccount(CreateAccountDTO dto);

    Account updateAccount(UpdateAccountDTO dto);

    UserDetails loadUserByUsername(String username);

    Optional<Account> findById(Long id);

    Long updatePassword(UpdateAccountPwdDTO dto);

    AccountPublic findOnePublicAccount(String username);
}
