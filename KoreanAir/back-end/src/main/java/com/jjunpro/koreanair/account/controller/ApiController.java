package com.jjunpro.koreanair.account.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.jjunpro.koreanair.account.domin.Account;
import com.jjunpro.koreanair.account.security.tokens.PostAuthorizationToken;
import com.jjunpro.koreanair.account.service.UserServiceImpl;

@RestController
@RequestMapping("/api/member")
@CrossOrigin
public class ApiController {
	
	@Autowired
	private UserServiceImpl memberTaskService;
	
	// 생성 CREATE or UADATE
	@PostMapping("")
	@PreAuthorize("isAnonymous()")
	public ResponseEntity<?> insertMember(
		@Valid @RequestBody Account account,
		BindingResult result
	) {
		if(!memberTaskService.findById(account.getUserId()).isEmpty()) 
		{
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("error", "이미 존재하는 아이디 입니다.");
			
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		Account newAccount = memberTaskService.saveOrUpdateMemberTask(account);
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
