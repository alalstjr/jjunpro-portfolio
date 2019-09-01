package com.jjunpro.koreanair.project.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.jjunpro.koreanair.project.dto.AccountFormLoginDTO;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken{

	// 직렬화 설정	
	private static final long serialVersionUID = 6466086836829977212L;

	public PreAuthorizationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public PreAuthorizationToken(AccountFormLoginDTO dto) {
		this(dto.getUserId(), dto.getPassword());
	}

	@Override
	public String getPrincipal() {
		return (String)super.getPrincipal();
	}
	
	@Override
	public String getCredentials() {
		return (String)super.getCredentials();
	}
}
