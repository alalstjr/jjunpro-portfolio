package com.backend.project.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.backend.project.security.context.AccountContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    private static String signingKey = "jjunproProject";

    public String generateToken(AccountContext account) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("jjunpro")
                    .withClaim("USERID", account.getAccount().getUserId())
                    .withClaim("USERNICKNAME", account.getAccount().getNickname())
                    .withClaim("USER_ROLE", account.getAccount().getUserRole().getKey())
                    .withClaim("EXP", new Date(System.currentTimeMillis() + 864000000))
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
