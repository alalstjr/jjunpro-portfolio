package com.backend.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
    String message() default "비밀번호가 일치하지 않습니다.";

    String password();

    String passwordRe();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
