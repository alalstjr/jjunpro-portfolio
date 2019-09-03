package com.jjunpro.koreanair.project.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.dto.AccountSaveDTO;
import com.jjunpro.koreanair.project.security.jwts.JwtDecoder;
import com.jjunpro.koreanair.project.security.token.PostAuthorizationToken;
import com.jjunpro.koreanair.project.serviceImpl.AccountServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class); 
	
	private AccountServiceImpl accountServiceImpl; 

	// Create Or Update
	@PostMapping("")
	@PostAuthorize("isAnonymous()")
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
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> adminAuthCheck(
			Authentication authentication
			) {
		PostAuthorizationToken token = (PostAuthorizationToken)authentication;
		log.info("관리자 로그인 - {}", token.getAccountContext().getAccount());
		
		return new ResponseEntity<String>(token.getAccountContext().getUsername(), HttpStatus.OK);
	} 
	
	@GetMapping("/go")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String getUsername(Authentication authentication) {
		PostAuthorizationToken token = (PostAuthorizationToken)authentication;
		return token.getAccountContext().getUsername();
	} 
}
 