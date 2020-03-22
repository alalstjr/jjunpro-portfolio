package com.jjunpro.project.validator;

import com.jjunpro.project.validator.WordFilterValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 사용자로부터 받은 { String DATA } 에 금지된 단어가 있는지 확인합니다.
 */
@Documented
@Target({ TYPE, METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WordFilterValidator.class)
public @interface WordFilter {
    String message() default "금지된 단어가 포함되어 있습니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


