package com.backend.project.annotation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class MaxFileValidator implements ConstraintValidator<MaxFile, MultipartFile[]> {
    private String _message;

    @Value("${max-upload-count}")
    private Integer _maxUploadCount;

    @Override
    public void initialize(MaxFile constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(
            MultipartFile[] value,
            ConstraintValidatorContext context
    ) {
        // Client 에서 file 을 받은 경우
        if (value != null) {
            if (value.length > _maxUploadCount) {
                context
                        .buildConstraintViolationWithTemplate(_message)
                        .addConstraintViolation();

                return false;
            }
        }

        return true;
    }
}
