package ua.company.controller.command.admin.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.company.controller.command.Command;
import ua.company.model.dto.tariff.TariffDto;
import ua.company.model.service.TariffService;
import ua.company.util.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class AdminCreateTariffCommand extends Command {
    private final TariffService tariffService;
    private final ValidationService validationService;

    public AdminCreateTariffCommand(TariffService tariffService, ValidationService validationService) {
        this.tariffService = tariffService;
        this.validationService = validationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestData = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();
        TariffDto tariffDto = objectMapper.readValue(requestData, TariffDto.class);
        validationService.validate(tariffDto);



        return null;
    }
}
