package com.jjunpro.koreanair.project.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {
	
	private static final long serialVersionUID = 2034183440499532298L;

	private JwtPreProcessingToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public JwtPreProcessingToken(String token) {
		this(token, token.length());
	}
}
