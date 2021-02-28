package ua.company.controller.command.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.mindrot.jbcrypt.BCrypt;

import ua.company.constants.Path;
import ua.company.controller.command.Command;
import ua.company.model.dto.SubscriptionDto;
import ua.company.model.service.SubscriptionService;
import ua.company.response.ApiResponse;
import ua.company.util.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SubscribeCommand extends Command {
    private final SubscriptionService subscriptionService;
    private final ValidationService validationService;

    public SubscribeCommand(SubscriptionService subscriptionService, ValidationService validationService) {
        this.subscriptionService = subscriptionService;
        this.validationService = validationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestData = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        SubscriptionDto subscriptionDto = objectMapper.readValue(requestData, SubscriptionDto.class);
        validationService.validate(subscriptionDto);

        subscriptionDto.setPassword(BCrypt.hashpw(subscriptionDto.getPassword(), BCrypt.gensalt()));
        subscriptionService.subscribe(subscriptionDto);

        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle bundle = ResourceBundle.getBundle("lang/res", locale);
        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_OK, bundle.getString("order_created_message"));
        request.setAttribute(Path.API_RESPONSE_ATTRIBUTE, apiResponse);

        return Path.COMMAND_API_RESPONSE;
    }
}
