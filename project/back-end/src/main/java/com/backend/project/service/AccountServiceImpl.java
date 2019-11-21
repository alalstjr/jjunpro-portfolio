package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.dto.AccountPwdUpdateDTO;
import com.backend.project.dto.AccountSaveDTO;
import com.backend.project.dto.AccountUpdateDTO;
import com.backend.project.projection.AccountPublic;
import com.backend.project.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository account;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<Account> findByUserId(String userId) {
        return account.findByUserId(userId);
    }

    @Override
    public Optional<Account> findByNickname(String nickname) {
        return account.findByNickname(nickname);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return account.findByEmail(email);
    }

    @Override
    public AccountPublic findOnePublicAccount(String userId) {
        return account.findOnePublicAccount(userId);
    }

    @Override
    public List<AccountPublic> findByPublicAccountList() {
        return account.findByPublicAccountList();
    }

    @Override
    public Account save(AccountSaveDTO dto) {

        String rawPassword = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        dto.setPassword(encodedPassword);

        return account.save(dto.toEntity());
    }

    @Override
    public Long update(AccountUpdateDTO dto) {
        return account.update(dto.toEntity());
    }

    @Override
    public Long pwdUpdate(AccountPwdUpdateDTO dto) {

        String rawPassword = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        dto.setPassword(encodedPassword);

        return account.pwdUpdate(dto);
    }
}
