package com.jjunpro.koreanair.account.security.tokens;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.jjunpro.koreanair.account.domin.UserRole;

public class JwtPostProcessingToken extends UsernamePasswordAuthenticationToken {

	private JwtPostProcessingToken(
			Object principal, 
			Object credentials,
			Collection<? extends GrantedAuthority> authorities) 
	{
		super(principal, credentials, authorities);
	}

    public JwtPostProcessingToken(String username, UserRole role) {
        super(username, "1234", parseAuthorities(role));
    }

    private static Collection<? extends GrantedAuthority> parseAuthorities(UserRole role) 
    {
        return Arrays
        		.asList(role)
        		.stream()
        		.map(r -> new SimpleGrantedAuthority(r.getRoleName()))
        		.collect(Collectors.toList());
    }
    
    public String getUserid() 
    {
    	return (String)super.getPrincipal();
    }
    
    public String getPassword() 
    {
    	return (String)super.getCredentials();
    }
}
