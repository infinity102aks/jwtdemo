package com.ankit.scheduler.validators;

import com.ankit.scheduler.configurations.NotEmptyEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyEnumValidator implements ConstraintValidator<NotEmptyEnum, Enum<?>> {

    @Override
    public void initialize(NotEmptyEnum constraintAnnotation) {
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value != null;
    }
}