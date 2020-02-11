package com.backend.project.annotation;

import com.backend.project.domain.Account;
import com.backend.project.service.AccountService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class EmailmatchValidator implements ConstraintValidator<EmailMatch, String> {
    private       String         _message;
    private final AccountService accountService;

    @Override
    public void initialize(EmailMatch constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Account> byUserId = accountService.findByEmail(value);
        if (!byUserId.isEmpty()) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
