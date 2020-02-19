package com.jjunpro.project.annotation;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.service.AccountService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class NickNameMatchValidator implements ConstraintValidator<NickNameMatch, String> {
    private       String            _message;
    private final AccountRepository accountRepository;

    /*
     * initialize() 메소드는 어노테이션으로 받은 값을 해당 필드에 초기화 선언을 합니다.
     * */
    @Override
    public void initialize(NickNameMatch constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        Optional<Account> byNickname = accountRepository.findByNickname(value);
        if (!byNickname.isEmpty()) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
