package com.jjunpro.koreanair.project.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.jjunpro.koreanair.project.security.context.AccountContext;
import com.jjunpro.koreanair.project.security.jwts.JwtDecoder;
import com.jjunpro.koreanair.project.security.token.JwtPreProcessingToken;
import com.jjunpro.koreanair.project.security.token.PostAuthorizationToken;

import lombok.AllArgsConstructor;

/**
 * 서포트할 Token의 종류는 Jwt 인증 전 Token JwtPreProcessingToken 입니다.
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private JwtDecoder jwtDecoder;
	
    @Override
    public Authentication authenticate(
    		Authentication authentication
    		) {
    	
    	String token = (String)authentication.getPrincipal();
        AccountContext context = jwtDecoder.decodeJwt(token);

        return PostAuthorizationToken.getTokenFormAccountContext(context);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtPreProcessingToken.class.isAssignableFrom(aClass);
    }
}