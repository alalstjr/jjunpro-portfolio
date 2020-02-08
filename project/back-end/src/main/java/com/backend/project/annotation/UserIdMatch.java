package com.backend.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Documented
@Constraint(validatedBy = UserIdMatchValidator.class)
@Target({ METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserIdMatch {
    String message() default "이미 존재하는 아이디입니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
