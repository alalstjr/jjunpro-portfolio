package com.jjunpro.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 사용자로부터 받은 { DATA id } 와 접근하려는 { DB DATA id } 가 같은지 확인합니다.
 * <p>
 * checkDomain 값은 필수입니다. 조회하려는 Domain 의 이름을 추가합니다.
 * Domain 이름은 소문자로 통일 입력합니다.
 * 만약 Domain 정보를 추가하려면 AccountUtill.class -> switch 문에 추가하면 됩니다.
 *
 * @see com.jjunpro.project.util.AccountUtil
 */
@Documented
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserDataMatchValidator.class)
public @interface UserDataMatch {
    String message() default "접근하는 정보가 맞지 않습니다.";

    String id();

    String domain();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
