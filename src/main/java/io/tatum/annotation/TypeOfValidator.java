package io.tatum.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TypeOfValidator implements ConstraintValidator<TypeOf, Object> {

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
