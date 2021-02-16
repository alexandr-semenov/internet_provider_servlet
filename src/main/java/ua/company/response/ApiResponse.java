package ua.company.response;

import java.util.List;

public class ApiResponse {
    private int status;
    private String message;
    private List<String> errors;

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(int statusCode, List<String> errors) {
        this.status = statusCode;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}