package com.jjunpro.project.validator;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.util.AccountUtil;
import java.lang.reflect.Field;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * Spring Security Context 접근하여 로그인된 사용자의 { DATA } 정보를 받아옵니다.
 * <p>
 * AccountUtill 접근한 사용자가 DB 에 존재하는지 확인하는 메소드입니다.
 * <p>
 * 최종적으로 { 접근하려는 DATA id } 그리고 { 접근하는 DB DATA id } 같은지 비교합니다.
 */
@RequiredArgsConstructor
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String  _message;
    private String  _password;
    private String  _passwordRe;
    private String  _oldPassword;
    private boolean _encoder;

    private final PasswordEncoder passwordEncoder;
    private final AccountUtil     accountUtill;
    private final SecurityContext securityContext = SecurityContextHolder.getContext();

    /*
     * initialize() 메소드는 어노테이션으로 받은 값을 해당 필드에 초기화 선언을 합니다.
     * */
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        _message = constraintAnnotation.message();
        _password = constraintAnnotation.password();
        _passwordRe = constraintAnnotation.passwordRe();
        _oldPassword = constraintAnnotation.oldPassword();
        _encoder = constraintAnnotation.encoder();
    }

    @Override
    public boolean isValid(
            Object value,
            ConstraintValidatorContext context
    ) {
        boolean valid;

        /*
         * passwordCheck[0] : password
         * passwordCheck[1] : passwordRe
         * passwordCheck[2] : oldPassword
         * */
        final String[] passwordCheck = new String[3];

        Field[] declaredFields = value.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            field.setAccessible(true);

            if (field.getName().equals("password")) {
                try {
                    passwordCheck[0] = (String) field.get(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.getName().equals("passwordRe")) {
                try {
                    passwordCheck[1] = (String) field.get(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.getName().equals("oldPassword")) {
                try {
                    passwordCheck[2] = (String) field.get(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        /* 입력한 password || passwordRe 가 동일한지 확인합니다. */
        valid = passwordCheck[0] == null && passwordCheck[1] == null
                || passwordCheck[0] != null && passwordCheck[0].equals(passwordCheck[1]);

        if (!valid) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addPropertyNode(_password)
                    .addConstraintViolation();

            return false;
        }

        /* 비밀번호 변경인 경우 oldPassword 가 동일한지 확인합니다. */
        if (_encoder) {
            Optional<Account> accountData = accountUtill
                    .accountInfo(securityContext.getAuthentication());

            valid = passwordEncoder.matches(
                    passwordCheck[2],
                    accountData
                            .get()
                            .getPassword()
            );

            if (!valid) {
                context
                        .buildConstraintViolationWithTemplate("이전 비밀번호가 같지 않습니다.")
                        .addPropertyNode(_oldPassword)
                        .addConstraintViolation();
            }
        }

        return valid;
    }
}
