package com.jjunpro.koreanair.project.security.hendlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jjunpro.koreanair.project.dto.TokenDTO;
import com.jjunpro.koreanair.project.respone.JwtProcessRespone;
import com.jjunpro.koreanair.project.security.context.AccountContext;
import com.jjunpro.koreanair.project.security.jwts.JwtFactory;
import com.jjunpro.koreanair.project.security.token.PostAuthorizationToken;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private JwtFactory factory;
	
	private JwtProcessRespone jwtProcessRespone;
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest req, 
			HttpServletResponse res,
			Authentication auth
			) throws IOException, ServletException {
		
		PostAuthorizationToken token = (PostAuthorizationToken) auth;
		AccountContext context = (AccountContext) token.getPrincipal();
		
		String tokenString = factory.generateToken(context);
		
		String username = token.getAccountContext().getAccount().getUsername();
		String userId = token.getAccountContext().getAccount().getUserId();
		
		jwtProcessRespone.jwtRespone(res, tokenDto(tokenString, username, userId));
	}
	 
	private TokenDTO tokenDto(String token, String username, String userId) {
		return new TokenDTO(token, username, userId);
	}
}
