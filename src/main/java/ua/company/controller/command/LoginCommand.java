package ua.company.controller.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;
import ua.company.exception.ApiException;
import ua.company.model.dto.UserDto;
import ua.company.model.entity.User;
import ua.company.model.service.UserService;
import ua.company.util.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoginCommand extends Command {
    private final UserService userService;
    private final ValidationService validationService;

    public LoginCommand() {
        this.userService = new UserService();
        this.validationService = new ValidationService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ApiException {
        HttpSession session = request.getSession();

        String requestData = request.getReader().lines().collect(Collectors.joining());

        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.readValue(requestData, UserDto.class);

        validationService.validate(userDto);

        User user = userService.getUserByUsername(userDto.getUsername());
        Boolean check = BCrypt.checkpw(userDto.getPassword(), user.getPassword());

        return null;
    }
}
