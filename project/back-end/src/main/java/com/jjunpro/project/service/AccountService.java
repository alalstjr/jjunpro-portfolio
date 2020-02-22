package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface AccountService {

    Account createAccount(CreateAccountDTO dto);

    Account updateAccount(UpdateAccountDTO dto);

    UserDetails loadUserByUsername(String username);
}
