package com.jjunpro.koreanair.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.koreanair.dto.AccountFormLoginDTO;
import com.jjunpro.koreanair.security.hendlers.FormLoginAuthenticationFailuerHandler;
import com.jjunpro.koreanair.security.tokens.PreAuthorizationToken;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter{

	private AuthenticationSuccessHandler authenticationSuccessHandler;
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	public FormLoginFilter(
			String defaultUrl, 
			AuthenticationSuccessHandler successHandler, 
			FormLoginAuthenticationFailuerHandler failuerHandler) 
	{
		super(defaultUrl);
		this.authenticationSuccessHandler = successHandler;
		this.authenticationFailureHandler = failuerHandler;
	}
	
	protected FormLoginFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res
			)
			throws AuthenticationException, IOException, ServletException 
	{
		AccountFormLoginDTO dto = new ObjectMapper()
				.readValue( req.getReader(), AccountFormLoginDTO.class );
		
		PreAuthorizationToken token = new PreAuthorizationToken(dto);
		
		/*
		 * PreAuthorizationToken 해당 객체에 맞는 Provider를 
		 * getAuthenticationManager 해당 메서드가 자동으로 찾아서 연결해 준다.
		 * */
		
		return super
				.getAuthenticationManager()
				.authenticate(token);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res, 
			FilterChain chain,
			Authentication authResult
			) throws IOException, ServletException 
	{
		this
		.authenticationSuccessHandler
		.onAuthenticationSuccess(req, res, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res,
			AuthenticationException failed
			) throws IOException, ServletException 
	{
		this
		.authenticationFailureHandler
		.onAuthenticationFailure(req, res, failed);
	}
	
}
