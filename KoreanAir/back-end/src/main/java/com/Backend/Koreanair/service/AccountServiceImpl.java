package com.backend.koreanair.service;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.dto.AccountSaveDTO;
import com.backend.koreanair.projection.AccountPublic;
import com.backend.koreanair.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository account;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Page<AccountPublic> findByUserPublic(Pageable pageable) {
        return account.findByUserPublic(pageable);
    }

    @Override
    public Optional<Account> findByUserId(String userId) {
        return account.findByUserId(userId);
    }

    @Override
    public Account saveOrUpdate(AccountSaveDTO dto) {

        String rawPassword = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        dto.setPassword(encodedPassword);

        return account.save(dto.toEntity());
    }
}
