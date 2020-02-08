package com.backend.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = WordFilterValidator.class)
@Target({ TYPE, METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface WordFilter {
    String message() default "금지된 단어가 포함되어 있습니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
