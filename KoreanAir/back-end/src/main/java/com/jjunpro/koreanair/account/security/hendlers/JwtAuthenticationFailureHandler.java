package com.jjunpro.koreanair.account.security.hendlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFailureHandler.class);
	
	public JwtAuthenticationFailureHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest req, 
			HttpServletResponse res,
			AuthenticationException e
			) throws IOException, ServletException 
	{
		System.out.println("onAuthenticationFailure");
		log.error(e.getMessage());
	}

}
