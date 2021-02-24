package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.TariffDaoImpl;
import ua.company.model.dto.product.TariffProductDto;
import ua.company.model.dto.tariff.TariffDto;
import ua.company.model.entity.Tariff;

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

    public Tariff getTariffById(Long id) throws ApiException {
        TariffDaoImpl tariffDao = daoFactory.createTariffDao();
        Tariff tariff;

        try {
            tariff = tariffDao.findById(id);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (tariff == null) {
            throw new ApiException(Arrays.asList("tariff_not_found_exception"), HttpServletResponse.SC_NOT_FOUND);
        }

        return tariff;
    }

    public boolean createTariff(TariffDto tariffDto) throws ApiException {
        TariffDaoImpl tariffDao = daoFactory.createTariffDao();

        try {
            tariffDao.create(tariffDto);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return true;
    }

    public boolean updateTariff(TariffDto tariffDto) throws ApiException {
        TariffDaoImpl tariffDao = daoFactory.createTariffDao();
        try {
            tariffDao.update(tariffDto);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return true;
    }
}
