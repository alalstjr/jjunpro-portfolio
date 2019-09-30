package com.backend.koreanair.security.hendler;

import com.backend.koreanair.dto.TokenDTO;
import com.backend.koreanair.security.context.AccountContext;
import com.backend.koreanair.security.jwt.JwtFactory;
import com.backend.koreanair.security.token.PostAuthorizationToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtFactory factory;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        AccountContext context = (AccountContext) token.getPrincipal();

        String tokenString = factory.generateToken(context);

        String username = context.getAccount().getUsername();
        String userId = context.getAccount().getUserId();

        processRespone(response, writeDTO(tokenString, username, userId));
    }

    private TokenDTO writeDTO(String token, String username, String userId) {
        return new TokenDTO(token, username, userId);
    }

    private void processRespone(
            HttpServletResponse res,
            TokenDTO dto
    ) throws JsonProcessingException, IOException {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}
