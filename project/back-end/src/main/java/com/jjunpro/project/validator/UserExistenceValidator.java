package com.jjunpro.project.validator;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.util.AccountUtil;
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
 * 사용자의 { DATA account } 가 DB 에 존재하는지 확인합니다.
 */
@RequiredArgsConstructor
public class UserExistenceValidator implements ConstraintValidator<UserExistence, Object> {

    private       String          _message;
    private       String          _id;
    private final SecurityContext securityContext = SecurityContextHolder.getContext();
    private final AccountUtil     accountUtill;

    @Override
    public void initialize(UserExistence constraintAnnotation) {
        _message = constraintAnnotation.message();
        _id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(
            Object value,
            ConstraintValidatorContext context
    ) {
        Optional<Account> accountData = accountUtill.accountInfo(securityContext.getAuthentication());

        if (accountData.isEmpty()) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addPropertyNode(_id)
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
