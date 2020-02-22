package com.jjunpro.project.util;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.repository.UniversityRepository;
import com.jjunpro.project.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountUtil {

    private final AccountRepository    accountRepository;
    private final UniversityRepository universityRepository;

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
