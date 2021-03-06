package ua.company.controller.command.main;

import ua.company.constants.Page;
import ua.company.controller.command.Command;
import ua.company.model.entity.Product;
import ua.company.model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InternetTvCommand extends Command {
    private final ProductService productService;

    public InternetTvCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Product product = productService.getProductByName("internet_tv");

        request.setAttribute("product", product);

        return Page.MAIN_INTERNET_TV_PAGE;
    }
}
