package com.backend.project.annotation;

import com.backend.project.domain.Account;
import com.backend.project.service.AccountService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * AccountService 사용자의 DB 정보를 조회하는 { Service class Bean } 이 필수입니다.
 */
@RequiredArgsConstructor
public class UserIdMatchValidator implements ConstraintValidator<UserIdMatch, String> {
    private       String         message;
    private final AccountService accountService;

    @Override
    public void initialize(UserIdMatch constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Account> byUserId = accountService.findByUserId(value);
        if (!byUserId.isEmpty()) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
