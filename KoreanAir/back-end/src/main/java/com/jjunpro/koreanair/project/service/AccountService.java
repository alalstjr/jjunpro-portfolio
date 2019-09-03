package com.jjunpro.koreanair.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.dto.AccountSaveDTO;

@Service
public interface AccountService {
	public AccountEntity saveOrUpdate(AccountSaveDTO dto);
	public Optional<AccountEntity> findByUserId(String dto);
	public List<AccountEntity> findAll();
}
