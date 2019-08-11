package com.jjunpro.koreanair.security.hendlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.koreanair.dto.AccountTokenDTO;
import com.jjunpro.koreanair.security.context.AccountContext;
import com.jjunpro.koreanair.security.jwt.JwtFactory;
import com.jjunpro.koreanair.security.tokens.PostAuthorizationToken;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private JwtFactory factory;
	
	private ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest req, 
			HttpServletResponse res,
			Authentication auth
			) throws IOException, ServletException {
		// Token 값을 정형화된 DTO를 만들어서 res 으로 내려주는 역활을 수행한다.
		// 인증결과 객체 auth 를 PostAuthorizationToken객체 변수에 담아줍니다. 
		PostAuthorizationToken token = (PostAuthorizationToken) auth;
		AccountContext context = (AccountContext) token.getPrincipal();
		
		String tokenString = factory.generateToken(context);
		
		String username = token.getAccountContext().getAccount().getUsername();
		String userId = token.getAccountContext().getAccount().getUserId();
		
		processRespone(res, writeDto(tokenString, username, userId));
	}
	 
	private AccountTokenDTO writeDto(String token, String username, String userId) {
		return new AccountTokenDTO(token, username, userId);
	}
	
	private void processRespone(
			HttpServletResponse res,
			AccountTokenDTO dto
			) throws JsonProcessingException, IOException {
		res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		res.setStatus(HttpStatus.OK.value());
		res.getWriter().write(objectMapper.writeValueAsString(dto));
	}

}