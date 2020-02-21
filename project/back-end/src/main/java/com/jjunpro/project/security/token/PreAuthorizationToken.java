package com.jjunpro.project.security.token;

import com.jjunpro.project.dto.FormLoginDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(
            Object principal,
            Object credentials
    ) {
        super(
                principal,
                credentials
        );
    }

    public PreAuthorizationToken(FormLoginDTO loginDTO) {
        this(
                loginDTO.getUsername(),
                loginDTO.getPassword()
        );
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
