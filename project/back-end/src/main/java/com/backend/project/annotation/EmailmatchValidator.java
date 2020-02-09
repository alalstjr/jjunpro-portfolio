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
public class EmailmatchValidator implements ConstraintValidator<EmailMatch, String> {
    private       String         message;
    private final AccountService accountService;

    @Override
    public void initialize(EmailMatch constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Account> byUserId = accountService.findByEmail(value);
        if (!byUserId.isEmpty()) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
