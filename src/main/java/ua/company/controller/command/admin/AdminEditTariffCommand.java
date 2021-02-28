package ua.company.controller.command.admin;

import ua.company.constants.Page;
import ua.company.controller.command.Command;
import ua.company.model.entity.Product;
import ua.company.model.entity.Tariff;
import ua.company.model.service.ProductService;
import ua.company.model.service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.List;

public class AdminEditTariffCommand extends Command {
    private final TariffService tariffService;
    private final ProductService productService;

    public AdminEditTariffCommand(TariffService tariffService, ProductService productService) {
        this.tariffService = tariffService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tariffId = request.getParameter("id");
        Tariff tariff = tariffService.getTariffById(Long.valueOf(tariffId));
        request.setAttribute("tariff", tariff);

        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);

        return Page.ADMIN_EDIT_TARIFF_PAGE;
    }
}
