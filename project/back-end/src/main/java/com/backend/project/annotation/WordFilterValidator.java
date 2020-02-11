package com.backend.project.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WordFilterValidator implements ConstraintValidator<WordFilter, String> {
    private String _message;

    /*
     * initialize() 메소드는 어노테이션으로 받은 값을 해당 필드에 초기화 선언을 합니다.
     * */
    @Override
    public void initialize(WordFilter constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String[] checkChar = { "ADMIN", "Admin", "admin", "관리자", "최고관리자", "어드민", "운영자", "pugjjig", "푹찍", "씨발", "씨1발", "섹스", "섹1스", "보지", "보1지", "야한", "야1한", "19금", "병신", "병1신", "니애미", "자지", "자1지", "망가", "망1가", "FUCK", "fuck", "Fuck", "SEX", "sex"};

        for (String keyword : checkChar) {
            if (value.contains(keyword)) {
                context
                        .buildConstraintViolationWithTemplate(_message)
                        .addConstraintViolation();

                return false;
            }
        }

        return true;
    }
}
