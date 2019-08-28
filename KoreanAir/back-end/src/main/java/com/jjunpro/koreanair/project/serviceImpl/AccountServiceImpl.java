package com.jjunpro.koreanair.project.serviceImpl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.dto.AccountSaveDTO;
import com.jjunpro.koreanair.project.repositoriy.AccountRepository;
import com.jjunpro.koreanair.project.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;
	
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public AccountEntity saveOrUpdate(AccountSaveDTO dto) {
		
		String rawPassword = dto.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		dto.setPassword(encodedPassword);
		
		return accountRepository.save(dto.toEntity());
	}
	
	@Override
	public Optional<AccountEntity> findByUserId(String dto) {
		
		return accountRepository.findByUserId(dto);
	}
}
