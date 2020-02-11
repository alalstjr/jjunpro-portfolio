package com.backend.project.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {
    private final Map<String, String> errors;

    public static ValidationResult create(Errors bindingResult, MessageSource messageSource, Locale locale) {
        Map<String, String> errors = new HashMap<String, String>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }

        return new ValidationResult(errors);
    }
}