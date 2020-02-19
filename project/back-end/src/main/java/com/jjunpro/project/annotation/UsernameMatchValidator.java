package com.jjunpro.project.annotation;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class UsernameMatchValidator implements ConstraintValidator<UsernameMatch, String> {

    private       String            _message;
    private final AccountRepository accountRepository;

    @Override
    public void initialize(UsernameMatch constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        Optional<Account> byUserId = accountRepository.findByUsername(value);
        if (!byUserId.isEmpty()) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
