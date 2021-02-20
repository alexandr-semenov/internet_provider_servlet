package ua.company.controller.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;
import ua.company.constants.Path;
import ua.company.constants.RoleName;
import ua.company.exception.ApiException;
import ua.company.model.dto.user.UserDto;
import ua.company.model.entity.Role;
import ua.company.model.entity.User;
import ua.company.model.service.UserService;
import ua.company.response.ApiResponse;
import ua.company.util.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ApiLoginCommand extends Command {
    private final UserService userService;
    private final ValidationService validationService;

    public ApiLoginCommand(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String forward = null;
        HttpSession session = request.getSession();

        String requestData = request.getReader().lines().collect(Collectors.joining());

        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.readValue(requestData, UserDto.class);

        validationService.validate(userDto);

        User user = userService.getUserByUsername(userDto.getUsername());
        boolean passwordCheck = BCrypt.checkpw(userDto.getPassword(), user.getPassword());

        if (!passwordCheck) {
            throw new ApiException(Arrays.asList("username_and_password_not_found_error"), HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!user.isActive()) {
            throw new ApiException(Arrays.asList("username_is_disabled"), HttpServletResponse.SC_BAD_REQUEST);
        }

        Role role = user.getRole();

        if (role.getName().equals(RoleName.ROLE_ADMIN)) {
            forward = Path.COMMAND_ADMIN;
        }

        if (role.getName().equals(RoleName.ROLE_USER)) {
            forward = Path.COMMAND_CABINET;
        }

        session.setAttribute("user", user);

        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_OK, forward);
        request.setAttribute(Path.API_RESPONSE_ATTRIBUTE, apiResponse);

        return Path.COMMAND_API_RESPONSE;
    }
}
