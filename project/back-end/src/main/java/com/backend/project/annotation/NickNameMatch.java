package com.backend.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = NickNameMatchValidator.class)
@Target({ TYPE, METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NickNameMatch {
    String message() default "이미 존재하는 닉네임입니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
