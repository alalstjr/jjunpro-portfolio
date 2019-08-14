package com.jjunpro.koreanair.project.serviceImpl;

import org.springframework.stereotype.Service;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.dto.AccountSaveRequestDto;
import com.jjunpro.koreanair.project.repositoriy.AccountRepository;
import com.jjunpro.koreanair.project.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;
	
	@Override
	public AccountEntity saveOrUpdate(AccountSaveRequestDto dto) {
		
		return accountRepository.save(dto.toEntity());
	}
}
