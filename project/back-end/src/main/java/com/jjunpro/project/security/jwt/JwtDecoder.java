package com.jjunpro.project.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jjunpro.project.context.AccountContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class JwtDecoder {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AccountContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new NoSuchElementException("유효한 토큰아 아닙니다."));

        String username = decodedJWT
                .getClaim("USERNAME")
                .asString();

        String role = decodedJWT
                .getClaim("ROLE")
                .asString();

        return new AccountContext(username, role);
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256("jjunproProject");
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}
