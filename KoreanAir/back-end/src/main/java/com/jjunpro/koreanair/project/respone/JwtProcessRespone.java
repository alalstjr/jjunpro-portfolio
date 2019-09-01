package com.jjunpro.koreanair.project.respone;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.koreanair.project.dto.TokenDTO;
import com.jjunpro.koreanair.project.security.hendlers.FormLoginAuthenticationSuccessHandler;

import lombok.AllArgsConstructor;

/**
 *	JWT 인증 알고리즘에 따른 결과 안내문 
 *	(사용자, 개발자) 에게 적절한 안내문을 출력해주는 Class
 */
@Component
@AllArgsConstructor
public class JwtProcessRespone {

	private ObjectMapper objectMapper;
	
	private static final Logger log = LoggerFactory.getLogger(FormLoginAuthenticationSuccessHandler.class);
	
	/**
	 *	JWT 반환 Respone 
	 */	
	public void jwtRespone(
			HttpServletResponse res,
			TokenDTO dto
			) throws JsonProcessingException, IOException {
		
		log.info("유저 Token 생성 - {}", objectMapper.writeValueAsString(dto));
		
		res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		res.setStatus(HttpStatus.OK.value());
		res.getWriter().print(objectMapper.writeValueAsString(dto));
	}
	
	/**
	 *	JWT 인증 실패 Respone 
	 */
	public void jwtAuthenticationFailureRespone(
			HttpServletResponse res,
			AuthenticationException e
			) throws JsonProcessingException, IOException {
		
		log.error("JWT 인증 실패 - {}", e.getMessage());
		
		res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		res.setStatus(HttpStatus.ACCEPTED.value());
		res.getWriter().print(objectMapper.writeValueAsString(e));
	}
	
	/**
	 *	JWT 상태 검사 Respone 
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public void jwtStateCheckRespone() throws JsonProcessingException, IOException {
		
		HttpServletResponse res = (HttpServletResponse)RequestContextHolder.currentRequestAttributes();
		
		Map<String, Object> json = new HashMap<String, Object>(); 
		json.put("error", "올바른 JWT 정보가 아닙니다.");
		
		String e = "올바른 JWT 정보가 아닙니다.";

		log.error(e);
		
		res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		res.setStatus(HttpStatus.ACCEPTED.value());
		res.getWriter().print(objectMapper.writeValueAsString(json));
	}
	
	/**
	 *	JWT 로그인 실패 Respone 
	 */
	public void formLoginFailuerHandlerRespone(
			HttpServletResponse res,
			AuthenticationException e
			) throws JsonProcessingException, IOException {
		
		log.info("로그인 인증 실패 - {}", e.getMessage());
		
		res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		res.setStatus(HttpStatus.ACCEPTED.value());
		res.getWriter().print(objectMapper.writeValueAsString(e));
	}
}
