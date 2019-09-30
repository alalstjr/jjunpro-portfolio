package com.backend.koreanair.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.koreanair.enums.EnumMapper;
import com.backend.koreanair.enums.UserRole;
import com.backend.koreanair.security.context.AccountContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class JwtDecoder {

    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);

    @Autowired
    private EnumMapper enumMapper;

    public AccountContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token)
                .orElseThrow(() -> new NoSuchElementException("유효한 토큰아 아닙니다."));

        String username = decodedJWT.getClaim("USERNAME").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();

        // JWT 토큰 값의 ROLE 값이 ROLE ENUM 값에 존재하는지 체크합니다.
        UserRole roles = enumMapper.getRoleByName(role);

        // UserRole String 형을 SimpleGrantedAuthority 바꿔주는 메서드 실행
        List<SimpleGrantedAuthority> userRole = enumMapper.userRoleList(roles);

        return new AccountContext(username, userRole);
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256("jjunproProject");
            JWTVerifier verifier = JWT.require(algorithm).build();

            jwt = verifier.verify(token);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }

}
