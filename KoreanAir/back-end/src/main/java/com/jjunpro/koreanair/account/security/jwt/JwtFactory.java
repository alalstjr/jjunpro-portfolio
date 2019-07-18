package com.jjunpro.koreanair.account.security.jwt;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jjunpro.koreanair.account.security.AccountContext;

@Component
public class JwtFactory {

	private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);
	
	private static String signingKey = "jwttest";
	
	public String generateToken(AccountContext context) {
		String token = null;
		try {
			token = JWT.create()
					.withIssuer("jjunpro")
					.withClaim("USERNAME", context.getAccount().getUserId())
					.withClaim("USER_ROLE", context.getAccount().getUserRole().getRoleName())
					.sign(generateAlgorithm());
			
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		
		return token;
	}
	
	private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
		return Algorithm.HMAC256(signingKey);
	}

}
