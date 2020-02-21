package com.jjunpro.project.security.provider;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.security.jwt.JwtDecoder;
import com.jjunpro.project.security.token.JwtPreProcessingToken;
import com.jjunpro.project.security.token.PostAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String         token   = (String) authentication.getPrincipal();
        AccountContext context = jwtDecoder.decodeJwt(token);

        return PostAuthorizationToken.getTokenFromAccountContext(context);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
