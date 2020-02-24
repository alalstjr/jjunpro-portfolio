package com.jjunpro.project.annotation;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.enums.ColumnType;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.util.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class DataMatchValidator implements ConstraintValidator<DataMatch, String> {

    private String     _message;
    private ColumnType _column;

    private final AccountRepository accountRepository;
    private final AccountUtil       accountUtil;
    private final SecurityContext   securityContext = SecurityContextHolder.getContext();

    /*
     * initialize() 메소드는 어노테이션으로 받은 값을 해당 필드에 초기화 선언을 합니다.
     * */
    @Override
    public void initialize(DataMatch constraintAnnotation) {
        _message = constraintAnnotation.message();
        _column = constraintAnnotation.column();
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        boolean           result      = false;
        Optional<Account> byUserId;
        Optional<Account> accountData = Optional.empty();

        /* 접근하는 사용자가 익명사용자인지 확인합니다. */
        if (!securityContext
                .getAuthentication()
                .getPrincipal()
                .equals("anonymousUser")) {
            accountData = accountUtil.accountInfo(securityContext.getAuthentication());
        }

        /* { DATA UPDATE } 클라이언트 _column 정보와 { DATA DB } 정보와 동일하지 않다면 검증실패 */
        switch (_column) {
            case NICKNAME:
                byUserId = accountRepository.findByNickname(value);

                /* Account Update 경우 기본 정보를 그대로 저장하기 위한 조건문 */
                if (accountData.isPresent()) {
                    if (byUserId.isPresent()) {
                        if (!byUserId
                                .get()
                                .getNickname()
                                .equals(accountData
                                        .get()
                                        .getNickname())) {
                            result = true;
                        }
                    }
                }
                /* Account Create 경우 */
                else {
                    if (byUserId.isPresent()) {
                        result = true;
                    }
                }

                break;

            case EMAIL:
                /* EMAIL 값은 체크는 필수가 아닙니다. */
                if (value != null && !value.isEmpty()) {
                    byUserId = accountRepository.findByEmail(value);

                    if (byUserId.isPresent() && accountData.isPresent()) {
                        if (!byUserId
                                .get()
                                .getEmail()
                                .equals(accountData
                                        .get()
                                        .getEmail())) {
                            result = true;
                        }
                    }
                }

            case USERNAME:
                byUserId = accountRepository.findByUsername(value);

                if (byUserId.isPresent()) {
                    result = true;
                }
        }

        if (result) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
