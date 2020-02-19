package com.jjunpro.project.annotation;

import com.jjunpro.project.repository.AccountRepository;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * 사용자로부터 받은 { DATA userId } 가 DB 에 존재하는지 확인합니다.
 * <p>
 * 사용자의 { DATA DB } 정보 조회를 위해서 *{ AccountRepository.class Bean } 필수입니다.
 *
 * @see AccountRepository
 */
@Documented
@Target({ METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameMatchValidator.class)
public @interface UsernameMatch {
    String message() default "이미 존재하는 아이디입니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
