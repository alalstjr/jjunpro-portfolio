package com.jjunpro.project.validator;

import com.jjunpro.project.validator.SubMenuDataValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Target({ TYPE, METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SubMenuDataValidator.class)
public @interface SubMenuData {
    
    String message() default "이름과 가격은 필수입력 입니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
