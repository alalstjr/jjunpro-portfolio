package com.jjunpro.project.annotation;

import com.jjunpro.project.validator.PasswordMatchValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 사용자로부터 받은 { DATA password } 와 { DATA passwordRe } 가 같은지 확인합니다.
 * <p>
 * encoder true 인경우 사용자로부터 받은 { DATA OldPassword } 와 { DB DATA password } 가 같은지 encoder 하여 확인합니다.
 */
@Documented
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    String message() default "비밀번호가 일치하지 않습니다.";

    String password();

    String passwordRe();

    String oldPassword() default "";

    boolean encoder() default false;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
