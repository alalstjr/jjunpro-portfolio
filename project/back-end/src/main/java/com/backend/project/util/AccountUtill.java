package com.backend.project.util;

import com.backend.project.domain.Account;
import com.backend.project.security.context.AccountContext;
import com.backend.project.security.jwt.HeaderTokenExtractor;
import com.backend.project.security.jwt.JwtDecoder;
import com.backend.project.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class AccountUtill {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private HeaderTokenExtractor headerTokenExtractor;

    /*
    *   유저 정보를 { DB에서 } 가져옵니다.
    *   Controller 에서 사용가능합니다.
    *   매개변수로 Authentication 필수 입니다.
    */
    public Optional<Account> accountInfo(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return accountService.findByUserId(userDetails.getUsername());
    }

    /*
     *   JWToken의 유저 정보를 가져옵니다.
     *   유저의 로그인 상태를 확인하는 메소드가 아닙니다.
     *   단순히 로그인 || 비로그인 구분만 하는 메소드 입니다.
     *   Controller 에서 사용가능합니다.
     *   매개변수로 HttpServletRequest 필수 입니다.
     *
     *   만약 JWToken 값의 유저가 DB에 존재하지 않으면 에러가 발생합니다.
     */
    public Account accountJWT(HttpServletRequest request) throws IOException {
        if(request.getHeader("Authorization") != null) {
            String tokenExtractor = headerTokenExtractor.extract(request.getHeader("Authorization"));
            AccountContext accountContext = jwtDecoder.decodeJwt(tokenExtractor);
            UserDetails userDetails = (UserDetails) accountContext;
            return accountService.findByUserId(userDetails.getUsername()).get();
        } else {
            return null;
        }
    }
}
