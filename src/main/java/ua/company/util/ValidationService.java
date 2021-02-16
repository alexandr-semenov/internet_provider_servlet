package ua.company.util;

import org.apache.log4j.Logger;
import ua.company.exception.ApiException;
import ua.company.model.dto.ValidationDto;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationService {
    private static final Logger LOGGER = Logger.getLogger(ValidationService.class);
    private final Validator validator;

    public ValidationService() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public boolean validate(ValidationDto dto) throws ApiException {
        Set<ConstraintViolation<ValidationDto>> violations = validator.validate(dto);
        List<String> messages = new ArrayList<>();

        if (!violations.isEmpty()) {
            for (ConstraintViolation<ValidationDto> violation : violations) {
                messages.add(violation.getMessage());
                LOGGER.error(violation.getPropertyPath() + ": " + violation.getMessage());
            }

            throw new ApiException(messages, HttpServletResponse.SC_BAD_REQUEST);
        }

        return true;
    }
}
