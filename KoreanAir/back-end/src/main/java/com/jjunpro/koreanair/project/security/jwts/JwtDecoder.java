package com.jjunpro.koreanair.project.security.jwts;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jjunpro.koreanair.project.enums.EnumMapper;
import com.jjunpro.koreanair.project.security.context.AccountContext;

@Component
public class JwtDecoder {

	private EnumMapper enumMapper;
	
    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);

    public AccountContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token)
        		.orElseThrow(() -> new NoResultException("유효한 토큰아 아닙니다."));

        String username = decodedJWT.getClaim("USERNAME").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();

        // UserRole String 형을 SimpleGrantedAuthority 바꿔주는 메서드 실행        
		List<SimpleGrantedAuthority> userRole = enumMapper.userRoleListString(role);
        
        return new AccountContext(username, userRole);
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256("jwttest");
            JWTVerifier verifier = JWT.require(algorithm).build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }

}