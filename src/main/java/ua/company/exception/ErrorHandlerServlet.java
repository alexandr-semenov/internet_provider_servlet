package ua.company.exception;

import ua.company.constants.Path;
import ua.company.response.ApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ErrorHandlerServlet extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ApiException apiException = (ApiException) request.getAttribute("javax.servlet.error.exception");

        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle bundle = ResourceBundle.getBundle("lang/res", locale);

        List<String> translatedMessages = apiException.getMessages().stream().map(bundle::getString).collect(Collectors.toList());

        ApiResponse apiResponse = new ApiResponse(apiException.getStatusCode(), translatedMessages);
        request.setAttribute(Path.API_RESPONSE_ATTRIBUTE, apiResponse);

        request.getRequestDispatcher(Path.COMMAND_API_RESPONSE).forward(request, response);
    }
}
