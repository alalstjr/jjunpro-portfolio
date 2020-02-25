package com.jjunpro.project.annotation;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.enums.ColumnType;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class DataMatchValidator implements ConstraintValidator<DataMatch, String> {

    private String     _message;
    private ColumnType _column;

    private final AccountRepository accountRepository;
    private final AccountService    accountService;

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
        AccountContext    userDetails = null;

        /* 접근하는 사용자가 익명사용자인지 확인합니다. */
        if (!SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
                .equals("anonymousUser")) {

            UserDetails principal = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            String username = principal.getUsername();

            userDetails = (AccountContext) accountService.loadUserByUsername(username);
        }

        /* { DATA UPDATE } 클라이언트 _column 정보와 { DATA DB } 정보와 동일하지 않다면 검증실패 */
        switch (_column) {
            case NICKNAME:
                byUserId = accountRepository.findByNickname(value);

                /* Account Update 경우 기본 정보를 그대로 저장하기 위한 조건문 */
                if (userDetails != null) {
                    if (byUserId.isPresent()) {
                        if (!byUserId
                                .get()
                                .getNickname()
                                .equals(userDetails
                                        .getAccount()
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

                    if (byUserId.isPresent() && userDetails != null) {
                        if (!byUserId
                                .get()
                                .getEmail()
                                .equals(userDetails
                                        .getAccount()
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
