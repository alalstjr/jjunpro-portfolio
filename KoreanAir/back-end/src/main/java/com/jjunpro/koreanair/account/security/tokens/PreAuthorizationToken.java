package com.jjunpro.koreanair.account.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.jjunpro.koreanair.account.security.dtos.FormLoginDto;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

	private PreAuthorizationToken(String username, String password) 
	{
		super(username, password);
	}
	
	public PreAuthorizationToken(FormLoginDto dto) 
	{
		this(dto.getUserid(), dto.getPassword());
	}
	
	public String getUsername() 
	{
		System.out.println("pretoken");
		return (String)super.getPrincipal();
	}
	
	public String getUserPassword() 
	{
		return (String)super.getCredentials();
	}
}


/*
 * 인증 전 객체
 */