package ua.company.controller.command.cabinet;

import ua.company.constants.Path;
import ua.company.controller.command.Command;
import ua.company.response.ApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CabinetCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_OK, "cabinet page");
        request.setAttribute(Path.API_RESPONSE_ATTRIBUTE, apiResponse);

        return Path.COMMAND_API_RESPONSE;
    }
}
