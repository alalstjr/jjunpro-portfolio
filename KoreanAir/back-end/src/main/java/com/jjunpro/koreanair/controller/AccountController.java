package com.jjunpro.koreanair.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjunpro.koreanair.domain.Account;
import com.jjunpro.koreanair.dto.AccountSaveRequestDTO;
import com.jjunpro.koreanair.security.tokens.PostAuthorizationToken;
import com.jjunpro.koreanair.serviceImpl.AccountServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin
public class AccountController {
	
	private AccountServiceImpl userServiceImpl;
	
	//	private static final Logger log = LoggerFactory.getLogger(ApiController.class); 
	
	// 생성 CREATE or UADATE
	@PostMapping("")
	@PostAuthorize("isAnonymous()")
	public ResponseEntity<?> insertMember(
		@Valid @RequestBody AccountSaveRequestDTO account,
		BindingResult result
	) {
		
		if(!userServiceImpl.findByUserId(account.getUserId()).isEmpty()) 
		{
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("AuthenticationError", "이미 존재하는 아이디 입니다.");
			
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}

		Account newAccount = userServiceImpl.saveOrUpdateUser(account);
		return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
	}
	
	@GetMapping("/go")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String getUsername(
			Authentication authentication, 
			@RequestHeader(value="authorization") String accept
			)
	{
		PostAuthorizationToken token = (PostAuthorizationToken)authentication;
		return token.getAccountContext().getUsername();
	}
}
