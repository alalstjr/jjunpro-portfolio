package com.jjunpro.koreanair.service;

import java.util.Optional;

import com.jjunpro.koreanair.domain.Account;
import com.jjunpro.koreanair.dto.AccountSaveRequestDTO;

public interface AccountService {
	public Account saveOrUpdateUser(AccountSaveRequestDTO dto);
	public Optional<Account> findByUserId(String useId);
}
