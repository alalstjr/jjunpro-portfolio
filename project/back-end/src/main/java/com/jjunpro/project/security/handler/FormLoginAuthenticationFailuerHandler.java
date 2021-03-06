package com.jjunpro.project.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationFailuerHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(FormLoginAuthenticationFailuerHandler.class);

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        log.error(
                "JWT 인증 실패 - {}",
                exception
        );

        //        throw new IOException("인증 정보가 잘못되었습니다.");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.ACCEPTED.value());
        response
                .getWriter()
                .print(objectMapper.writeValueAsString(exception));
    }
}
