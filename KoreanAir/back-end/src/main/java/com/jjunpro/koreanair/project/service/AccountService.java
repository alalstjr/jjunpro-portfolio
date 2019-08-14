package com.jjunpro.koreanair.project.service;

import org.springframework.stereotype.Service;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.dto.AccountSaveRequestDto;

@Service
public interface AccountService {
	public AccountEntity saveOrUpdate(AccountSaveRequestDto dto);
}
