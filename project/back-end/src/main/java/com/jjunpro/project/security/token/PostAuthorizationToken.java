package com.jjunpro.project.security.token;

import com.jjunpro.project.context.AccountContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PostAuthorizationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(
                principal,
                credentials,
                authorities
        );
    }

    public PostAuthorizationToken getTokenFromAccountContext(AccountContext context) {
        return new PostAuthorizationToken(
                context,
                context.getPassword(),
                context.getAuthorities()
        );
    }
}
