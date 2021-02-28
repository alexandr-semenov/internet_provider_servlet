package ua.company.controller.command.admin;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.company.constants.Path;
import ua.company.controller.command.Command;
import ua.company.model.dto.user.UserIdDto;
import ua.company.model.service.UserService;
import ua.company.response.ApiResponse;
import ua.company.util.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminActivateUserCommand extends Command {
    private final UserService userService;
    private final ValidationService validationService;

    public AdminActivateUserCommand(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestData = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        UserIdDto userIdDto = objectMapper.readValue(requestData, UserIdDto.class);
        validationService.validate(userIdDto);

        userService.activateUserById(userIdDto.getId());

        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle bundle = ResourceBundle.getBundle("lang/res", locale);
        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_OK, bundle.getString("username_activated"));
        request.setAttribute(Path.API_RESPONSE_ATTRIBUTE, apiResponse);

        return Path.COMMAND_API_RESPONSE;
    }
}
