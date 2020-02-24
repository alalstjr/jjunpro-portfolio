package com.jjunpro.project.util;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Alarm;
import com.jjunpro.project.domain.Comment;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.repository.AlarmRepository;
import com.jjunpro.project.repository.CommentRepository;
import com.jjunpro.project.repository.UniversityRepository;
import com.jjunpro.project.security.jwt.HeaderTokenExtractor;
import com.jjunpro.project.security.jwt.JwtDecoder;
import com.jjunpro.project.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountUtil {

    private final AccountRepository    accountRepository;

    private final HeaderTokenExtractor headerTokenExtractor;
    private final JwtDecoder           jwtDecoder;

    /**
     * 유저 정보를 { DB 에서 } 가져옵니다.
     * Controller 에서 사용가능합니다.
     * 매개변수로 Authentication 필수 입니다.
     */
    public Optional<Account> accountInfo(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return accountRepository.findByUsername(userDetails.getUsername());
    }

    /**
     * JWToken의 유저 정보를 가져옵니다.
     * 유저의 로그인 상태를 확인하는 메소드가 아닙니다.
     * 단순히 로그인 || 비로그인 구분만 하는 메소드 입니다.
     * Controller 에서 사용가능합니다.
     * 매개변수로 HttpServletRequest 필수 입니다.
     * 로그인한 유저가 탐색한 University Data 의 uniLike 체크 상태를 파악하기 위해서 사용합니다.
     * <p>
     * 만약 JWToken 값의 유저가 DB에 존재하지 않으면 에러가 발생합니다.
     */
    public Account accountJWT(HttpServletRequest request) throws IOException {
        if (request.getHeader("Authorization") != null) {
            String      tokenExtractor = headerTokenExtractor.extract(request.getHeader("Authorization"));
            UserDetails userDetails    = jwtDecoder.decodeJwt(tokenExtractor);

            Optional<Account> byUsername = accountRepository.findByUsername(userDetails.getUsername());
            return byUsername.orElse(null);
        }
        else {
            return null;
        }
    }
}
