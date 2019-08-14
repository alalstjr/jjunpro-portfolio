package com.jjunpro.koreanair.project.security.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.jjunpro.koreanair.project.security.context.AccountContext;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = -8441128646024630165L;

	public PostAuthorizationToken(
			Object principal, 
			Object credentials,
			Collection<? extends GrantedAuthority> authorities
			) {
		super(principal, credentials, authorities);
	}

	/*
	 *	PostAuthorizationToken 생성하는 메서드  User 클래스르 상속받은 AccountContext 매개변수로 받아 생성
	 */
	public static PostAuthorizationToken getTokenFormAccountContext(AccountContext context) {
		
		return new PostAuthorizationToken(
				context, 
				context.getPassword(), 
				context.getAuthorities()
				);
	} 
	
	public AccountContext getAccountContext() {
		 return (AccountContext)super.getPrincipal();
	}
}
