package com.jjunpro.koreanair.account.security.jwt;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jjunpro.koreanair.account.exception.InvalidJwtException;
import com.jjunpro.koreanair.account.security.AccountContext;

@Component
public class JwtDecoder {

    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);

    public AccountContext decodeJwt(String token) 
    {
        DecodedJWT decodedJWT = isValidToken(token)
        		.orElseThrow(() -> new InvalidJwtException("유효한 토큰아 아닙니다."));

        String username = decodedJWT.getClaim("USERNAME").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();

        return new AccountContext(username, "$2a$10$p/ibqZb28DvK7vHk6s2WYO1ZGTIAgjCqI/knBlpyuLa8in6c8uiZa", role);
    }

    private Optional<DecodedJWT> isValidToken(String token) 
    {
        DecodedJWT jwt = null;

        try 
        {
            Algorithm algorithm = Algorithm.HMAC256("jwttest");
            JWTVerifier verifier = JWT.require(algorithm).build();

            jwt = verifier.verify(token);
        } 
        catch (Exception e) 
        {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }

}