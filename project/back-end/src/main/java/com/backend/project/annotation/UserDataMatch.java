package com.backend.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

/**
 * 사용자로부터 받은 { DATA id } 와 접근하려는 { DB DATA id } 가 같은지 확인합니다.
 */
@Documented
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserDataMatchValidator.class)
public @interface UserDataMatch {
    String message() default "접근하는 정보가 맞지 않습니다.";

    String id();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
