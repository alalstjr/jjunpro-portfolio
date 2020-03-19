package com.jjunpro.project.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class BindValidatorAspect {

    @Around("@annotation(com.jjunpro.project.annotation.BindValidator)")
    public Object validator(ProceedingJoinPoint joinPoint) throws Throwable {
        BindingResult bindingResult = null;
        String        parameterName;

        Object[] joinPointArgs = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method          method    = signature.getMethod();

        for (int i = 0; i < method.getParameters().length; i++) {
            parameterName = method.getParameters()[i].getName();
            if (parameterName.equals("bindingResult")) {
                bindingResult = (BindingResult) joinPointArgs[i];
            }
        }

        if (bindingResult != null && bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return joinPoint.proceed();
    }
}
