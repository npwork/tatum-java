package io.tatum.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.validation.*;
import java.util.Set;

@Log4j2
public class ObjectValidator {

    private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final static Validator validator = factory.getValidator();

    public static Boolean isValidated(Object value) {
        Set<ConstraintViolation<Object>> violations = validator.validate(value);
        for (ConstraintViolation<Object> violation : violations) {
            String message = null;
            for (Path.Node node : violation.getPropertyPath()) {
                message = String.format("%s %s", node.getName(), violation.getMessage());
            }
            // nested message
            if (StringUtils.isNotEmpty(message)) {
                log.error(message);
                return false;
            }
            return false;
        }
        return true;
    }
}
