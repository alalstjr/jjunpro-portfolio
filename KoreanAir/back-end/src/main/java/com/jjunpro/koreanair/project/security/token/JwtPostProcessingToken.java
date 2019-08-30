package com.jjunpro.koreanair.project.security.token;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtPostProcessingToken extends UsernamePasswordAuthenticationToken {
	
	private static final long serialVersionUID = -893474296225152063L;
	
	private JwtPostProcessingToken(
			Object principal, 
			Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

    public JwtPostProcessingToken(
    		String username, 
    		List<SimpleGrantedAuthority> role
    		) {
        super(username,  role);
    }
    
    public String getUserid() {
    	return (String)super.getPrincipal();
    }
    
    public String getPassword() {
    	return (String)super.getCredentials();
    }
}

