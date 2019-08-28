package com.jjunpro.koreanair.project.security.jwts;

import org.springframework.util.StringUtils;

public class HeaderTokenExtractor {

	/*
	 * HEADER_PREFIX
	 * header Authorization token 값의 표준이되는 변수 
	 */
	public static final String HEADER_PREFIX = "bearer "; 

	public String extract(String header) {
		/*
		 * - Token 값이 올바르지 않은경우 -
		 * header 값이 비어있거나 또는 HEADER_PREFIX 값보다 짧은 경우
		 * 이셉션을(예외)를 던져주어야 합니다.
		 */
		if(StringUtils.isEmpty(header) | header.length() < HEADER_PREFIX.length()) {
			throw new InvalidJwtException("올바른 토큰 정보가 아닙니다.");
		}

		/*
		 * - Token 값이 존재하는 경우 -
		 * (bearer ) 부분만 제거 후 token 값 반환
		 */
		return header.substring(HEADER_PREFIX.length(), header.length());
	}
}
