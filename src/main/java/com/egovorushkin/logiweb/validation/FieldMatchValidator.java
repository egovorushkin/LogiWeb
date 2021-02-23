package com.egovorushkin.logiweb.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * Represent a validator for fields.
 * Compare two fields
 * implements {@link ConstraintValidator}
 *
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    /**
     * This method initialize two fields
     * @param constraintAnnotation
     */
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    /**
     *
     * @param value
     * @param context
     * @return true if two fields are equals and false if are not
     */
    @Override
    public boolean isValid(final Object value,
                           final ConstraintValidatorContext context) {
        boolean valid;
        final Object firstObj =
                new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
        final Object secondObj =
                new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

        valid = firstObj == null && secondObj == null
                || firstObj != null && firstObj.equals(secondObj);

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
