package com.jjunpro.koreanair.serviceImpl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jjunpro.koreanair.domain.Account;
import com.jjunpro.koreanair.dto.AccountSaveRequestDTO;
import com.jjunpro.koreanair.repository.AccountRepository;
import com.jjunpro.koreanair.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	private AccountRepository accountRepository;
	
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Account saveOrUpdateUser(AccountSaveRequestDTO dto) {
		String rawPassword = dto.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		dto.setPassword(encodedPassword);
		
		return accountRepository.save(dto.toEntity());
	}

	@Override
	public Optional<Account> findByUserId(String userId) {
		return accountRepository.findByUserId(userId);
	}
}
