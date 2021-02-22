package ua.company.controller.command.product;

import com.google.gson.Gson;
import ua.company.constants.Path;
import ua.company.controller.command.Command;
import ua.company.model.dto.product.TariffDto;
import ua.company.model.entity.Product;
import ua.company.model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductCommand extends Command {
    private final ProductService productService;

    public ProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String productId = request.getParameter("id");
        Product product = productService.getProductById(Long.valueOf(productId));

        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle bundle = ResourceBundle.getBundle("lang/res", locale);

        List<TariffDto> tariffs = product.getTariffs().stream()
                .map(tariff -> new TariffDto(tariff.getId(),
                bundle.getString(tariff.getName())))
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String json = gson.toJson(tariffs);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        out.write(json);
        out.close();

        return Path.COMMAND_EMPTY;
    }
}
