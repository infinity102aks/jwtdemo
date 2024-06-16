package com.ankit.scheduler.configurations;

import com.ankit.scheduler.validators.NotEmptyEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotEmptyEnumValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyEnum {

    String message() default "Value cannot be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}