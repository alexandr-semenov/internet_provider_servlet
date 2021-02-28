package ua.company.controller.command;

import com.google.gson.Gson;

import ua.company.constants.Path;
import ua.company.response.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ApiResponseCommand extends Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApiResponse apiResponse = (ApiResponse) request.getAttribute("apiResponse");

        Gson gson = new Gson();
        String json = gson.toJson(apiResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(apiResponse.getStatus());

        PrintWriter out = response.getWriter();
        out.write(json);
        out.close();

        return Path.COMMAND_EMPTY;
    }
}
