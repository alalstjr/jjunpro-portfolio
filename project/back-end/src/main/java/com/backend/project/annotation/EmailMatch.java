package com.backend.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = EmailmatchValidator.class)
@Target({ TYPE, METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailMatch {
    String message() default "이미 존재하는 이메일입니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
