package com.usman.springboot.config;

import com.usman.springboot.annotation.EnumValidator;
import io.micrometer.core.instrument.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 22-12-2022
 * @since : 1.0.0
 */
public class EnumValueValidator implements ConstraintValidator<EnumValidator, String> {

    private EnumValidator enumValidator;

    @Override
    public void initialize(EnumValidator enumValidator) {
        this.enumValidator = enumValidator;
    }

    @Override
    public boolean isValid(
        String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {

        if (enumValidator.isBlankAllowed() && StringUtils.isBlank(valueForValidation)) {
            return true;
        }

        for (Enum<?> enumValue : this.enumValidator.enumClass().getEnumConstants()) {

            if (isValidEnumValue(valueForValidation, enumValue)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidEnumValue(String valueForValidation, Enum<?> enumValue) {
        return StringUtils.isNotBlank(valueForValidation)
            && valueForValidation.equalsIgnoreCase(enumValue.toString());
    }
}
