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
 * 사용자의 { DATA account } 가 DB 에 존재하는지 확인합니다.
 */
@Documented
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserExistenceValidator.class)
public @interface UserExistence {
    String message() default "존재하지 않는 유저 정보입니다.";

    String id();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
