package ua.company.exception;

import java.util.List;

public class ApiErrorResponse {
    List<String> errors;

    public ApiErrorResponse() {
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
