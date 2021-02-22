package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.ProductDaoImpl;
import ua.company.model.entity.Product;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class ProductService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public Product getProductByName(String name) throws ApiException {
        ProductDaoImpl productDao = daoFactory.createProductDao();
        Product product;

        try {
            product = productDao.findByName(name);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (product == null) {
            throw new ApiException(Arrays.asList("product_not_found_exception"), HttpServletResponse.SC_NOT_FOUND);
        }

        return product;
    }

    public Product getProductById(Long id) throws ApiException {
        ProductDaoImpl productDao = daoFactory.createProductDao();
        Product product;

        try {
            product = productDao.findById(id);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (product == null) {
            throw new ApiException(Arrays.asList("product_not_found_exception"), HttpServletResponse.SC_NOT_FOUND);
        }

        return product;
    }

    public List<Product> getAllProducts() throws ApiException {
        ProductDaoImpl productDao = daoFactory.createProductDao();
        List<Product> products;

        try {
            products = productDao.findAll();
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (products.isEmpty()) {
            throw new ApiException(Arrays.asList("product_not_found_exception"), HttpServletResponse.SC_NOT_FOUND);
        }

        return products;
    }
}
