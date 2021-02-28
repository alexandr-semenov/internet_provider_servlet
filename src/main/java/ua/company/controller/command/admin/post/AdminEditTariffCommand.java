package ua.company.controller.command.admin.post;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.company.constants.Path;
import ua.company.controller.command.Command;
import ua.company.model.dto.tariff.TariffDto;
import ua.company.model.service.TariffService;
import ua.company.response.ApiResponse;
import ua.company.util.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminEditTariffCommand extends Command {
    private final TariffService tariffService;
    private final ValidationService validationService;

    public AdminEditTariffCommand(TariffService tariffService, ValidationService validationService) {
        this.tariffService = tariffService;
        this.validationService = validationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestData = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        TariffDto tariffDto = objectMapper.readValue(requestData, TariffDto.class);
        validationService.validate(tariffDto);

        tariffService.updateTariff(tariffDto);

        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle bundle = ResourceBundle.getBundle("lang/res", locale);
        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_OK, bundle.getString("tariff_updated"));
        request.setAttribute(Path.API_RESPONSE_ATTRIBUTE, apiResponse);

        return Path.COMMAND_API_RESPONSE;
    }
}
