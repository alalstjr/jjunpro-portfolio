package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountDTO;
import org.springframework.stereotype.Service;

public interface AccountService {

    public Account createAccount(CreateAccountDTO dto);

    public Account updateAccount(UpdateAccountDTO dto);
}
