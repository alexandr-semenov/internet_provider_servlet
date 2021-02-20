package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.TariffDaoImpl;
import ua.company.model.dto.product.TariffProductDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class TariffService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<TariffProductDto> getAllTariffWithProduct() throws ApiException {
        TariffDaoImpl tariffDao = daoFactory.createTariffDao();
        List<TariffProductDto> tariffProductDtoList;

        try {
            tariffProductDtoList = tariffDao.findAllTariffWithProduct();
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (tariffProductDtoList.isEmpty()) {
            throw new ApiException(Arrays.asList("tariff_not_found_exception"), HttpServletResponse.SC_NOT_FOUND);
        }

        return tariffProductDtoList;
    }
}
