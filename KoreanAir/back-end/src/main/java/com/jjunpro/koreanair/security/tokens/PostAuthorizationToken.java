package com.jjunpro.koreanair.security.tokens;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.jjunpro.koreanair.security.context.AccountContext;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken{

	private PostAuthorizationToken(
			Object principal, 
			Object credentials,
			Collection<? extends GrantedAuthority> authorities
			) {
		super(principal, credentials, authorities);
	}
	
	public static PostAuthorizationToken getTokenFromAccountContext(AccountContext context) {
		
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
