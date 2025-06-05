package com.example.demo.Service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;



@Constraint(validatedBy = RegisterValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegisterChecked {

        String message() default "user register validation failed";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};

}
