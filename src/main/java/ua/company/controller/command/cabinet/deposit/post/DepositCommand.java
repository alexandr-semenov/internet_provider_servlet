package ua.company.controller.command.cabinet.deposit.post;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.company.constants.Path;
import ua.company.controller.command.Command;
import ua.company.model.dto.AmountDto;
import ua.company.model.entity.User;
import ua.company.model.service.AccountService;
import ua.company.response.ApiResponse;
import ua.company.util.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DepositCommand extends Command {
    private final AccountService accountService;
    private final ValidationService validationService;

    public DepositCommand(AccountService accountService, ValidationService validationService) {
        this.accountService = accountService;
        this.validationService = validationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestData = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        AmountDto amountDto = objectMapper.readValue(requestData, AmountDto.class);
        validationService.validate(amountDto);

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        user = accountService.depositAccount(user, amountDto.getAmount());

        user = accountService.debitAccount(user, user.getSubscription().getPrice());

        session.setAttribute("user", user);

        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle bundle = ResourceBundle.getBundle("lang/res", locale);
        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_OK, bundle.getString("success_deposit"));
        request.setAttribute(Path.API_RESPONSE_ATTRIBUTE, apiResponse);

        return Path.COMMAND_API_RESPONSE;
    }
}
