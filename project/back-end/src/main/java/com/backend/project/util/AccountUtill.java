package com.backend.project.util;

import com.backend.project.domain.Account;
import com.backend.project.security.context.AccountContext;
import com.backend.project.security.jwt.HeaderTokenExtractor;
import com.backend.project.security.jwt.JwtDecoder;
import com.backend.project.service.AccountService;
import com.backend.project.service.AlarmService;
import com.backend.project.service.CommentService;
import com.backend.project.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class AccountUtill
{
    @Autowired
    private AccountService accountService;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private HeaderTokenExtractor headerTokenExtractor;

    /*
    *   유저 정보를 { DB에서 } 가져옵니다.
    *   Controller 에서 사용가능합니다.
    *   매개변수로 Authentication 필수 입니다.
    */
    public Optional<Account> accountInfo(Authentication authentication)
    {
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
    public Account accountJWT(HttpServletRequest request) throws IOException
    {
        if(request.getHeader("Authorization") != null)
        {
            String tokenExtractor = headerTokenExtractor.extract(request.getHeader("Authorization"));
            AccountContext accountContext = jwtDecoder.decodeJwt(tokenExtractor);
            UserDetails userDetails = (UserDetails) accountContext;
            return accountService.findByUserId(userDetails.getUsername()).get();
        }
        else
        {
            return null;
        }
    }

    /*
     *  Domain 의 특정 {id} DATA 값이 DB 에 존재하는지 확인하는 메소드입니다.
     *  String checkDomain 값은 검색하는 Domain 의 이름값입니다.
     *  DATA 가 존재하지 않을경우 NULL 을 반환합니다.
     */
    public boolean userDataCheck(Long id, Optional<Account> accountData, String checkDomain)
    {
        Long data = null;

        // 해당 데이터의 작성자 {id} 값을 가져옵니다.
        switch (checkDomain)
        {
            case "university" :
                data = universityService.findById(id).get().getAccount().getId();
                break;

            case "comment" :
                data = commentService.findById(id).get().getAccount().getId();
                break;

            case "account" :
                data = accountService.findById(id).get().getId();
                break;

            case "alarm" :
                data = alarmService.findById(id).get().getUserId();
                break;

            default:
                break;
        }

        // 해당 데이터의 작성자가 맞는지 검사합니다.
        if(!data.equals(accountData.get().getId()) || data == null)
        {
            return false;
        }
        return true;
    }
}
