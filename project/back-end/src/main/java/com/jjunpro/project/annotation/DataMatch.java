package com.jjunpro.project.annotation;

import com.jjunpro.project.enums.ColumnType;

import com.jjunpro.project.validator.DataMatchValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 사용자로부터 받은 { DATA value } 가 DB 에 존재하는지 확인합니다. 중복체크
 * <p>
 * 사용자의 { DATA DB } 정보 조회를 위해서 *{ AccountService.class Bean } 필수입니다.
 */
@Documented
@Target({ TYPE, METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataMatchValidator.class)
public @interface DataMatch {

    String message() default "이미 존재하는 정보입니다.";

    ColumnType column();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}