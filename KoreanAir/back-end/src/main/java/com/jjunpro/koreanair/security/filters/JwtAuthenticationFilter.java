package com.jjunpro.koreanair.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.jjunpro.koreanair.security.hendlers.JwtAuthenticationFailureHandler;
import com.jjunpro.koreanair.security.jwt.HeaderTokenExtractor;
import com.jjunpro.koreanair.security.tokens.JwtPreProcessingToken;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private JwtAuthenticationFailureHandler failureHandler; 
	private HeaderTokenExtractor extractor; 
	
	protected JwtAuthenticationFilter(RequestMatcher matcher) 
	{
		super(matcher);
	}
	
	public JwtAuthenticationFilter(
			RequestMatcher matcher,
			JwtAuthenticationFailureHandler failureHandler,
			HeaderTokenExtractor extractor
			) 
	{
		super(matcher);
		
		this.failureHandler = failureHandler;
		this.extractor = extractor;
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException 
	{
		String TokenPayload = req.getHeader("Authorization");
		
		JwtPreProcessingToken token = new JwtPreProcessingToken(extractor.extract(TokenPayload));
		
		return super.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res, 
			FilterChain chain,
			Authentication authResult
			) throws IOException, ServletException 
	{
		/*
		 *  SecurityContext 사용자 Token 저장소를 생성합니다.
		 *  SecurityContext 에 사용자의 인증된 Token 값을 저장합니다.
		 */
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);
		
		/*
		 *	FilterChain chain 해당 필터가 실행 후 다른 필터도 실행할 수 있도록 연결실켜주는 메서드 
		 */
		chain.doFilter(req, res);
	}

	@Override
	protected void unsuccessfulAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res,
			AuthenticationException failed
			) throws IOException, ServletException 
	{
		/*
		 *	로그인을 한 상태에서 Token값을 주고받는 상황에서 잘못된 Token값이라면
		 *	인증이 성공하지 못한 단계 이기때문에 잘못된 Token값을 제거합니다. 
		 */
		SecurityContextHolder.clearContext();
		
		this.unsuccessfulAuthentication(req, res, failed);
	}
}
