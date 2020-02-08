package com.backend.project.annotation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    private String password;
    private String passwordRe;
    private String message;

    /*
     * initialize() 메소드는 어노테이션으로 받은 값을 해당 필드에 초기화 선언을 합니다.
     * */
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        password = constraintAnnotation.password();
        passwordRe = constraintAnnotation.passwordRe();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            Object passwordCheck = BeanUtils.getProperty(
                    value,
                    password
            );
            Object passwordReCheck = BeanUtils.getProperty(
                    value,
                    passwordRe
            );

            valid = passwordCheck == null && passwordReCheck == null || passwordCheck != null && passwordCheck.equals(passwordReCheck);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (!valid) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation();
        }

        return valid;
    }
}
