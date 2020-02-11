package com.backend.project.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.FIELD;

/**
 * METHOD 의 TYPE 은 *{ MultipartFile } 입니다.
 * 사용자로부터 받은 { DATA file } 의 갯수의 최대값을 검증합니다.
 * 최대값은 서버와의 { DATA DB file } 의 갯수를 합한 최대값을 구하는 경우도 있기 때문에
 * 설정을 전역에서 사용할 수 있도록 *{ application.properties -> max-upload-count } 선언합니다.
 * <p>
 * properties 값이 없을경우 default 값은 { MaxFileValidator.class } 에서 설정가능 합니다.
 * <p>
 * application.properties 설정은 필수 입니다.
 */
@Documented
@Target({ TYPE, METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxFileValidator.class)
public @interface MaxFile {
    String message() default "최대 업로드 파일갯수를 넘었습니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
