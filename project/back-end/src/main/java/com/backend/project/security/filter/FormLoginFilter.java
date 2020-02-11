package com.backend.project.security.filter;

import com.backend.project.dto.SecurityFormLoginDTO;
import com.backend.project.security.token.PreAuthorizationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
        SecurityFormLoginDTO dto = objectMapper.readValue(
                request.getReader(),
                SecurityFormLoginDTO.class
        );

        if (dto.getUserId() == null) {
            throw new NoSuchElementException("아이디를 입력해 주세요.");
        }
        if (dto.getPassword() == null) {
            throw new NoSuchElementException("비밀번호를 입력해 주세요.");
        }

        // 사용자입력값이 존재하는지 검증 후 인증전 객체 Token 생성
        PreAuthorizationToken token = new PreAuthorizationToken(dto);

        // PreAuthorizationToken 해당 객체에 맞는 Provider를
        // getAuthenticationManager 해당 메서드가 자동으로 찾아서 연결해 준다.
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
