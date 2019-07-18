package com.jjunpro.koreanair.account.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjunpro.koreanair.account.security.tokens.PostAuthorizationToken;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@GetMapping("/go")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String getUsername(Authentication authentication)
	{
		PostAuthorizationToken token = (PostAuthorizationToken)authentication;
		return token.getAccountContext().getUsername();
	}
}
