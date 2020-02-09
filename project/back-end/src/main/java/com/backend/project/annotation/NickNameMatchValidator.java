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
public class NickNameMatchValidator implements ConstraintValidator<NickNameMatch, String> {
    private       String         message;
    private final AccountService accountService;

    /*
     * initialize() 메소드는 어노테이션으로 받은 값을 해당 필드에 초기화 선언을 합니다.
     * */
    @Override
    public void initialize(NickNameMatch constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Account> byNickname = accountService.findByNickname(value);
        if (!byNickname.isEmpty()) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
