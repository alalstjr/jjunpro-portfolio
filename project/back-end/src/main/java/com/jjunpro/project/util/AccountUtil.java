package com.jjunpro.project.util;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.repository.AccountRepository;
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
    private final UniversityRepository universityRepository;

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
            String         tokenExtractor = headerTokenExtractor.extract(request.getHeader("Authorization"));
            UserDetails    userDetails    = jwtDecoder.decodeJwt(tokenExtractor);

            Optional<Account> byUsername = accountRepository.findByUsername(userDetails.getUsername());
            return byUsername.orElse(null);
        }
        else {
            return null;
        }
    }

    /**
     * Domain 의 특정 { id } DATA 값이 DB 에 존재하는지 확인하는 메소드입니다.
     * String checkDomain 값은 검색하는 Domain 의 이름값입니다.
     * DATA 가 존재하지 않을경우 NULL 을 반환합니다.
     */
    public boolean dbDataMatch(
            Long id,
            Account accountData,
            String checkDomain
    ) {
        Long data = null;

        // 해당 데이터의 작성자 {id} 값을 가져옵니다.
        switch (checkDomain) {
            case "account":
                Optional<Account> accountDataDB = accountRepository.findById(id);
                if (accountDataDB.isPresent()) {
                    data = accountDataDB
                            .get()
                            .getId();
                }
                break;

            case "university":
                Optional<University> uniDataDB = universityRepository.findById(id);
                if (uniDataDB.isPresent()) {
                    data = uniDataDB
                            .get()
                            .getAccount()
                            .getId();
                }
                break;
/*
            case "comment":
                Optional<Comment> commentDataDB = commentService.findById(id);
                if (commentDataDB != null && commentDataDB.isPresent()) {
                    data = commentDataDB
                            .get()
                            .getAccount()
                            .getId();
                }
                break;

            case "alarm":
                Optional<Alarm> alarmDataDB = alarmService.findById(id);
                if (alarmDataDB != null && alarmDataDB.isPresent()) {
                    data = alarmDataDB
                            .get()
                            .getUserId();
                }
                break;*/

            default:
                break;
        }

        // 해당 데이터의 작성자가 맞는지 검사합니다.
        if (data != null) {
            return data.equals(accountData.getId());
        }

        return false;
    }
}
