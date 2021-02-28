package ua.company.controller.command.admin;

import ua.company.constants.Page;
import ua.company.controller.command.Command;
import ua.company.model.entity.Product;
import ua.company.model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.List;

public class AdminEditTariffsCommand extends Command {
    private final ProductService productService;

    public AdminEditTariffsCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);

        return Page.ADMIN_EDIT_TARIFFS_PAGE;
    }
}
