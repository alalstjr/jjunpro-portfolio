package com.jjunpro.project.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.security.jwt.JwtFactory;
import com.jjunpro.project.security.token.PostAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory   factory;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication
    ) throws IOException, ServletException {
        PostAuthorizationToken token   = (PostAuthorizationToken) authentication;
        AccountContext         context = (AccountContext) token.getPrincipal();

        String tokenString = factory.generateToken(context);
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

    }
}
