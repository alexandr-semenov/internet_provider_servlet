package ua.company.controller.command.subscription;

import ua.company.constants.Page;
import ua.company.controller.command.Command;
import ua.company.model.service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SubscriptionCommand extends Command {
    private final TariffService tariffService;

    public SubscriptionCommand(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("tariff_id", request.getParameter("tariff_id"));
        request.setAttribute("tariffProductDto", tariffService.getAllTariffWithProduct());

        return Page.SUBSCRIPTION_PAGE;
    }
}
