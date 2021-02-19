package ua.company.controller.command;

import ua.company.constants.Page;
import ua.company.constants.Path;
import ua.company.response.ApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_OK, Path.COMMAND_LOGIN);
        request.setAttribute(Path.API_RESPONSE_ATTRIBUTE, apiResponse);

        return Path.COMMAND_API_RESPONSE;
    }
}
