package com.backend.project.annotation;

import com.backend.project.domain.Account;
import com.backend.project.service.AccountService;
import com.backend.project.util.AccountUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class EmailmatchValidator implements ConstraintValidator<EmailMatch, String> {

    private String _message;

    private final AccountService  accountService;
    private final AccountUtill    accountUtill;
    private final SecurityContext securityContext = SecurityContextHolder.getContext();

    @Override
    public void initialize(EmailMatch constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        if (value != null && !value.isEmpty()) {
            Optional<Account> byUserId    = accountService.findByEmail(value);
            Optional<Account> accountData = accountUtill.accountInfo(securityContext.getAuthentication());

            if (byUserId.isPresent() && accountData.isPresent()) {
                //{ DATA UPDATE } 클라이언트 Email 정보와 { DATA DB } 정보와 동일하지 않다면 검증실패
                if (!byUserId
                        .get()
                        .getEmail()
                        .equals(accountData
                                .get()
                                .getEmail())) {
                    context
                            .buildConstraintViolationWithTemplate(_message)
                            .addConstraintViolation();

                    return false;
                }
            }
        }

        return true;
    }
}
