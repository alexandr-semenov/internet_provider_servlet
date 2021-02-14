package ua.company.exception;

import javax.servlet.ServletException;
import java.util.List;

public class ApiException extends ServletException {
    List<String> messages;

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(List<String> messages) {
        this.messages = messages;
    }

    public ApiException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public ApiException(Throwable rootCause) {
        super(rootCause);
    }

    public List<String> getMessages() {
        return messages;
    }
}
