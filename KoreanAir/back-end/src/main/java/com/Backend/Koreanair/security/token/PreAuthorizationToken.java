package com.backend.koreanair.security.token;

import com.backend.koreanair.dto.SecurityFormLoginDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PreAuthorizationToken(SecurityFormLoginDTO dto) {
        this(dto.getUserId(), dto.getPassword());
    }

    @Override
    public Object getCredentials() {
        return super.getCredentials();
    }

    @Override
    public Object getPrincipal() {
        return super.getPrincipal();
    }
}
