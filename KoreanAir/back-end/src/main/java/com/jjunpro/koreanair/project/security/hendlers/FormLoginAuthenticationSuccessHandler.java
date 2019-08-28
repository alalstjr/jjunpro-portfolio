package com.jjunpro.koreanair.project.security.hendlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.koreanair.project.dto.TokenDTO;
import com.jjunpro.koreanair.project.security.context.AccountContext;
import com.jjunpro.koreanair.project.security.jwts.JwtFactory;
import com.jjunpro.koreanair.project.security.token.PostAuthorizationToken;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private JwtFactory factory;
	
	private ObjectMapper objectMapper;
	
	private static final Logger log = LoggerFactory.getLogger(FormLoginAuthenticationSuccessHandler.class);
	
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
		
		processRespone(res, writeDto(tokenString, username, userId));
	}
	 
	private TokenDTO writeDto(String token, String username, String userId) {
		return new TokenDTO(token, username, userId);
	}
	
	private void processRespone(
			HttpServletResponse res,
			TokenDTO dto
			) throws JsonProcessingException, IOException {
		
//		log.info("등장 {}", objectMapper.writeValueAsString(dto));
		
		res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		res.setStatus(HttpStatus.OK.value());
		res.getWriter().write(objectMapper.writeValueAsString(dto));
	}

}
