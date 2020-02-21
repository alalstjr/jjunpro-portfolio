package com.jjunpro.project.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.project.dto.FormLoginDTO;
import com.jjunpro.project.security.handler.FormLoginAuthenticationFailuerHandler;
import com.jjunpro.project.security.handler.FormLoginAuthenticationSuccessHandler;
import com.jjunpro.project.security.token.PreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper                 objectMapper;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public FormLoginFilter(
            String defaultFilterProcessesUrl,
            ObjectMapper objectMapper,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler
    ) {
        super(defaultFilterProcessesUrl);
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, IOException, ServletException {
        // 사용자 입력값을 JSON 으로 변환 후 DTO 생성
        FormLoginDTO loginDTO = objectMapper.readValue(
                request.getReader(),
                FormLoginDTO.class
        );

        if (loginDTO.getUsername() == null) {
            throw new NoSuchElementException("아이디를 입력해 주세요.");
        }
        if (loginDTO.getPassword() == null) {
            throw new NoSuchElementException("비밀번호를 입력해 주세요.");
        }

        // 사용자입력값이 존재하는지 검증 후 인증전 객체 Token 생성
        PreAuthorizationToken token = new PreAuthorizationToken(loginDTO);

        /*
         * UsernamePasswordAuthenticationToken 이 최종적으로 Authentication.interface 를 상속받고 있으므로
         * PreAuthorizationToken 전달하여 인증이 가능합니다.
         *
         * PreAuthorizationToken.class Authentication 정보를 가지고
         * AuthenticationManager 전달하여 인증을 시도합니다.
         * */
        return super
                .getAuthenticationManager()
                .authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(
                request,
                response,
                authResult
        );
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        this.failureHandler.onAuthenticationFailure(
                request,
                response,
                failed
        );
    }
}
