package com.jjunpro.project.annotation;

import com.jjunpro.project.domain.SubMenu;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class SubMenuDataValidator implements ConstraintValidator<SubMenuData, Set<SubMenu>> {

    private String _message;

    @Override
    public void initialize(SubMenuData constraintAnnotation) {
        _message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(
            Set<SubMenu> value,
            ConstraintValidatorContext context
    ) {
        for (SubMenu sub : value) {
            if (sub.getName() == null || sub.getPrice() == null) {
                context
                        .buildConstraintViolationWithTemplate(_message)
                        .addConstraintViolation();

                return false;
            }
        }

        return true;
    }
}
