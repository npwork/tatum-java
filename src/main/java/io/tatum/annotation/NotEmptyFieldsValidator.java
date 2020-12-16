package io.tatum.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class NotEmptyFieldsValidator implements ConstraintValidator<NotEmptyFields, Object[]> {

    @Override
    public void initialize(NotEmptyFields notEmptyFields) {
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext context) {
        if (objects == null) {
            return true;
        }
        return Arrays.stream(objects).allMatch(nef -> nef != null);
    }

}
