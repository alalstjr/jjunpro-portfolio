package com.backend.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 사용자로부터 받은 { DATA email } 이 DB 에 존재하는지 확인합니다.
 */
@Documented
@Target({ TYPE, METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailmatchValidator.class)
public @interface EmailMatch {
    String message() default "이미 존재하는 이메일입니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
