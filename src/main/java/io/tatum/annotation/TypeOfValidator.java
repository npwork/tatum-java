package io.tatum.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Type of validator.
 */
public class TypeOfValidator implements ConstraintValidator<TypeOf, Object> {

    /**
     * The Type.
     */
    protected Class<?> type;

    @Override
    public void initialize(TypeOf oneOf) {
        this.type = oneOf.value();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return obj.getClass().getInterfaces()[0].equals(this.type);
    }
}
