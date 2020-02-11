package com.backend.project.annotation;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@RequiredArgsConstructor
public class MaxFileValidator implements ConstraintValidator<MaxFile, MultipartFile[]> {
    private       String      _message;
    private final Environment environment;

    @Override
    public void initialize(MaxFile constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
        Integer max = 3;

        if (environment.getProperty("max-upload-count") != null) {
            max = Integer.parseInt(Objects.requireNonNull(environment.getProperty("max-upload-count")));
        }

        if (value.length > max) {
            context
                    .buildConstraintViolationWithTemplate(_message)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
