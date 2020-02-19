package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountDTO;
import com.jjunpro.project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder   passwordEncoder;

    @Override
    public Account createAccount(CreateAccountDTO dto) {
        dto.encodePassword(passwordEncoder);

        return accountRepository.save(dto.toEntity());
    }

    @Override
    public Account updateAccount(UpdateAccountDTO dto) {
        Optional<Account> byId = accountRepository.findById(dto.getId());
        if(byId.isPresent()){
            dto.setUsername(byId.get().getUsername());
            dto.setPassword(byId.get().getPassword());
            dto.setRole(byId.get().getRole());
        }

        return accountRepository.save(dto.toEntity());
    }
}
