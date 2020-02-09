package com.backend.project.annotation;

import com.backend.project.domain.Account;
import com.backend.project.util.AccountUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * Spring Security Context 접근하여 로그인된 사용자의 { DATA } 정보를 받아옵니다.
 * <p>
 * AccountUtill 접근한 사용자가 DB 에 존재하는지 확인하는 메소드입니다.
 * <p>
 * 최종적으로 { 접근하려는 DATA id } 그리고 { 접근하는 DB DATA id } 같은지 비교합니다.
 */
@RequiredArgsConstructor
public class UserDataMatchValidator implements ConstraintValidator<UserDataMatch, Long> {
    private       String          _message;
    private final SecurityContext securityContext = SecurityContextHolder.getContext();
    private final AccountUtill    accountUtill;

    @Override
    public void initialize(UserDataMatch constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean           valid;
        Optional<Account> accountData = accountUtill.accountInfo(securityContext.getAuthentication());

        valid = accountData
                .get()
                .getId()
                .equals(value);

        if (!valid) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addConstraintViolation();
        }

        return valid;
    }
}
