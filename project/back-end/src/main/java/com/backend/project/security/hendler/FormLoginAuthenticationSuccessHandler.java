package com.backend.project.security.hendler;

import com.backend.project.dto.TokenDTO;
import com.backend.project.security.context.AccountContext;
import com.backend.project.security.jwt.JwtFactory;
import com.backend.project.security.token.PostAuthorizationToken;
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

        Long id = context.getAccount().getId();
        String userId = context.getAccount().getUserId();
        String nickname = context.getAccount().getNickname();

        processRespone(response, writeDTO(id, tokenString, userId, nickname));
    }

    private TokenDTO writeDTO(Long id, String token, String userId, String nickname) {
        return new TokenDTO(id, token, userId, nickname);
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
