package com.jjunpro.koreanair.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.jjunpro.koreanair.security.context.AccountContext;
import com.jjunpro.koreanair.security.jwt.JwtDecoder;
import com.jjunpro.koreanair.security.tokens.JwtPreProcessingToken;
import com.jjunpro.koreanair.security.tokens.PostAuthorizationToken;

/*
 * 서포트할 Token의 종류는 Jwt 인증 전 Token JwtPreProcessingToken 입니다.
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException 
    {
        String token = (String)authentication.getPrincipal();
        AccountContext context = null;

        return PostAuthorizationToken.getTokenFromAccountContext(context);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtPreProcessingToken.class.isAssignableFrom(aClass);
    }
}