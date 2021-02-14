package ua.company.exception;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ErrorHandlerServlet extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApiException apiException = (ApiException) request.getAttribute("javax.servlet.error.exception");
        List<String> errors = apiException.getMessages();

        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setErrors(errors);

        Gson gson = new Gson();
        String json = gson.toJson(errorResponse);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        out.print(json);
        out.flush();
    }
}
