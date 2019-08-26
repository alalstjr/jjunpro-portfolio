package com.jjunpro.koreanair.project.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.dto.AccountSaveDTO;
import com.jjunpro.koreanair.project.serviceImpl.AccountServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	private AccountServiceImpl accountServiceImpl; 

	// Create Or Update
	@PostMapping("")
	public ResponseEntity<?> saveOrUpdate(
			@Valid @RequestBody AccountSaveDTO dto,
			BindingResult result
			) {
		
		// DB Account ID Check		
		if(!accountServiceImpl.findByUserId(dto.getUserId()).isEmpty()) {
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("AuthenticationError", "이미 존재하는 아이디 입니다.");
			
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		// DB Account Create		
		AccountEntity newAccount = accountServiceImpl.saveOrUpdate(dto);
		
		return new ResponseEntity<AccountEntity>(newAccount, HttpStatus.CREATED);
	}
	
	// 
}
