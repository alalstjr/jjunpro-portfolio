package com.jjunpro.koreanair.project.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.dto.AccountSaveRequestDto;
import com.jjunpro.koreanair.project.serviceImpl.AccountServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	private AccountServiceImpl accountServiceImpl; 

	// Create Or Update
	@PostMapping("")
	public ResponseEntity<AccountEntity> saveOrUpdate(
			@Valid @RequestBody AccountSaveRequestDto dto,
			BindingResult result
			) {
		
		AccountEntity newAccount = accountServiceImpl.saveOrUpdate(dto);
		
		return new ResponseEntity<AccountEntity>(newAccount, HttpStatus.CREATED);
	}
}
