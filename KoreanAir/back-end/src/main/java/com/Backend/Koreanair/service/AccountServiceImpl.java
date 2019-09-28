package com.backend.koreanair.service;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.dto.AccountSaveDTO;
import com.backend.koreanair.projection.AccountPublic;
import com.backend.koreanair.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository account;

    @Override
    public Iterable<AccountPublic> findByUserPublic() {
        return account.findByUserPublic();
    }

    @Override
    public Account saveOrUpdate(AccountSaveDTO dto) {
        return account.save(dto.toEntity());
    }
}
