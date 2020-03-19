package com.jjunpro.project.validator;

import com.jjunpro.project.annotation.UserDataMatch;
import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Alarm;
import com.jjunpro.project.domain.Comment;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.enums.DomainType;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.repository.AlarmRepository;
import com.jjunpro.project.repository.CommentRepository;
import com.jjunpro.project.repository.UniversityRepository;
import com.jjunpro.project.service.AccountService;
import com.jjunpro.project.util.AccountUtil;
import java.lang.reflect.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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

    private String     _message;
    private String     _id;
    private DomainType _domain;

    private final AccountService       accountService;
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
        boolean valid = true;
        Long  idCheck;

        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = principal.getUsername();

        AccountContext userDetails = (AccountContext) accountService.loadUserByUsername(username);

        try {
            Field declaredField = value.getClass().getDeclaredField(_id);
            declaredField.setAccessible(true);
            idCheck = (Long) declaredField.get(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (idCheck != null) {
            valid = this.dbDataMatch(
                    idCheck,
                    userDetails.getAccount(),
                    _domain
            );
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
     * Domain 의 특정 { id } DATA 값이 DB 에 존재하는지 확인하는 메소드입니다. String checkDomain 값은 검색하는 Domain 의
     * 이름값입니다. DATA 가 존재하지 않을경우 NULL 을 반환합니다.
     */
    public boolean dbDataMatch(
            Long id,
            Account accountData,
            DomainType domainType
    ) {
        Long data = null;

        // 해당 데이터의 작성자 {id} 값을 가져옵니다.
        switch (domainType) {
            case ACCOUNT:
                Optional<Account> accountDataDB = accountRepository.findById(id);
                if (accountDataDB.isPresent()) {
                    data = accountDataDB
                            .get()
                            .getId();
                }
                break;

            case UNIVERSITY:
                Optional<University> uniDataDB = universityRepository.findById(id);
                if (uniDataDB.isPresent()) {
                    data = uniDataDB
                            .get()
                            .getAccount()
                            .getId();
                }
                break;

            case COMMENT:
                Optional<Comment> commentDataDB = commentRepository.findById(id);
                if (commentDataDB.isPresent()) {
                    data = commentDataDB
                            .get()
                            .getAccount()
                            .getId();
                }
                break;

            case ALARM:
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
