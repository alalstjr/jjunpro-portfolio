package com.jjunpro.project.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.dto.JWTokenDTO;
import com.jjunpro.project.security.jwt.JwtFactory;
import com.jjunpro.project.security.token.PostAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    private final JwtFactory factory;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        PostAuthorizationToken postAuthorizationToken = (PostAuthorizationToken) authentication;
        AccountContext         context                = (AccountContext) postAuthorizationToken.getPrincipal();

        String jwtoken = factory.generateToken(context);

        /* 응답으로 보내는 유저 정보를 담은 JWT DTO 를 생성합니다. */
        JWTokenDTO jwTokenDTO = new JWTokenDTO(
                context
                        .getAccount()
                        .getId(),
                jwtoken,
                context.getUsername(),
                context
                        .getAccount()
                        .getNickname()
        );

        processRespone(
                response,
                jwTokenDTO
        );
    }

    private void processRespone(
            HttpServletResponse response,
            JWTokenDTO dto
    ) throws JsonProcessingException, IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response
                .getWriter()
                .write(objectMapper.writeValueAsString(dto));
    }
}
