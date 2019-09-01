package com.jjunpro.koreanair.project.security.hendlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.jjunpro.koreanair.project.respone.JwtProcessRespone;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FormLoginAuthenticationFailuerHandler implements AuthenticationFailureHandler {
	
	JwtProcessRespone jwtProcessRespone;
	
	@Override
	public void onAuthenticationFailure(
			HttpServletRequest req, 
			HttpServletResponse res,
			AuthenticationException e
			) throws IOException, ServletException {
		
		jwtProcessRespone.formLoginFailuerHandlerRespone(res, e);
	}

}
