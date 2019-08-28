package com.jjunpro.koreanair.project.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private JwtAuthenticationFailureHandler failureHandler; 
	private HeaderTokenExtractor extractor; 
	
	public JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	public JwtAuthenticationFilter(
			RequestMatcher requiresAuthenticationRequestMatcher,
			JwtAuthenticationFailureHandler failureHandler,
			HeaderTokenExtractor extractor
			) {
		super(requiresAuthenticationRequestMatcher);
		
		this.failureHandler = failureHandler;
		this.extractor = extractor;
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request, 
			HttpServletResponse response
			)
			throws AuthenticationException, IOException, ServletException {
		return null;
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, 
			HttpServletResponse response,
			FilterChain chain,
			Authentication authResult
			) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(
			HttpServletRequest request, 
			HttpServletResponse response,
			AuthenticationException failed
			) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	
	
}
