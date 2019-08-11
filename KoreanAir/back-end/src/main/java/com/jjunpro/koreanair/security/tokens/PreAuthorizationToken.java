package com.jjunpro.koreanair.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.jjunpro.koreanair.dto.AccountFormLoginDTO;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

	private PreAuthorizationToken(String username, String password) {
		super(username, password);
	}
	
	public PreAuthorizationToken(AccountFormLoginDTO dto) {
		this(dto.getUserId(), dto.getPassword());
	}
	
	public String getUsername() {
		return (String)super.getPrincipal();
	}
	
	public String getUserPassword() {
		return (String)super.getCredentials();
	}
}