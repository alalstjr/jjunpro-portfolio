package com.jjunpro.koreanair.project.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.jjunpro.koreanair.project.security.hendlers.JwtAuthenticationFailureHandler;
import com.jjunpro.koreanair.project.security.jwts.HeaderTokenExtractor;
import com.jjunpro.koreanair.project.security.token.JwtPreProcessingToken;

/**
 * Token 을 내려주는 Filter 가 아닌  client 에서 받아지는 Token 을 서버 사이드에서 검증하는 클레스
 * SecurityContextHolder 보관소에 해당 Token 값의 인증 상태를 보관 하고 필요할때 마다 인증 확인 후 권한 상태 확인 하는 기능
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private HeaderTokenExtractor extractor; 
	
	private JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	public JwtAuthenticationFilter(
			RequestMatcher requiresAuthenticationRequestMatcher,
			JwtAuthenticationFailureHandler failureHandler,
			HeaderTokenExtractor extractor
			) {
		super(requiresAuthenticationRequestMatcher);
		
		this.extractor = extractor;
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res
			)
			throws AuthenticationException, IOException, ServletException {
		
		// JWT 값을 담아주는 변수 TokenPayload 		
		String tokenPayload = req.getHeader("Authorization");
		JwtPreProcessingToken token = new JwtPreProcessingToken(extractor.extract(tokenPayload));
		
		return super.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res,
			FilterChain chain,
			Authentication authResult
			) throws IOException, ServletException {
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
			) throws IOException, ServletException {
		/*
		 *	로그인을 한 상태에서 Token값을 주고받는 상황에서 잘못된 Token값이라면
		 *	인증이 성공하지 못한 단계 이기 때문에 잘못된 Token값을 제거합니다.
		 *	모든 인증받은 Context 값이 삭제 됩니다.  
		 */
		SecurityContextHolder.clearContext();
		
		this.unsuccessfulAuthentication(req, res, failed);
	}
	
	
	
}
