package com.jjunpro.project.annotation;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Alarm;
import com.jjunpro.project.domain.Comment;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.repository.AlarmRepository;
import com.jjunpro.project.repository.CommentRepository;
import com.jjunpro.project.repository.UniversityRepository;
import com.jjunpro.project.util.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * Spring Security Context 접근하여 로그인된 사용자의 { DATA } 정보를 받아옵니다.
 * <p>
 * AccountUtil 접근한 사용자가 DB 에 존재하는지 확인하는 메소드입니다.
 * <p>
 * 최종적으로 { 접근하려는 DATA id } 그리고 { 접근하는 DB DATA id } 같은지 비교합니다.
 */
@RequiredArgsConstructor
public class UserDataMatchValidator implements ConstraintValidator<UserDataMatch, Object> {

    private String _message;
    private String _id;
    private String _domain;

    private final SecurityContext securityContext = SecurityContextHolder.getContext();
    private final AccountUtil     accountUtil;

    private final AccountRepository    accountRepository;
    private final UniversityRepository universityRepository;
    private final CommentRepository    commentRepository;
    private final AlarmRepository      alarmRepository;


    @Override
    public void initialize(UserDataMatch constraintAnnotation) {
        _message = constraintAnnotation.message();
        _id = constraintAnnotation.id();
        _domain = constraintAnnotation.domain();
    }

    @Override
    public boolean isValid(
            Object value,
            ConstraintValidatorContext context
    ) {
        boolean valid   = true;
        String  idCheck = null;

        try {
            idCheck = BeanUtils.getProperty(
                    value,
                    _id
            );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (idCheck != null && !idCheck.isEmpty()) {
            Optional<Account> accountData = accountUtil.accountInfo(securityContext.getAuthentication());

            if (accountData.isPresent()) {
                valid = this.dbDataMatch(
                        Long.parseLong(idCheck),
                        accountData.get(),
                        _domain
                );
            }
        }

        if (!valid) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addPropertyNode(_id)
                    .addConstraintViolation();
        }

        return valid;
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

            case "comment":
                Optional<Comment> commentDataDB = commentRepository.findById(id);
                if (commentDataDB.isPresent()) {
                    data = commentDataDB
                            .get()
                            .getAccount()
                            .getId();
                }
                break;

            case "alarm":
                Optional<Alarm> alarmDataDB = alarmRepository.findById(id);
                if (alarmDataDB.isPresent()) {
                    data = alarmDataDB
                            .get()
                            .getUserId();
                }
                break;

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
