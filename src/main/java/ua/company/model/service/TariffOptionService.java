package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.TariffOptionDaoImpl;
import ua.company.model.dto.tariff.TariffOptionCreateDto;
import ua.company.model.dto.tariff.TariffOptionDeleteDto;
import ua.company.model.dto.tariff.TariffOptionUpdateDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class TariffOptionService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public boolean createTariffOption(TariffOptionCreateDto tariffOptionCreateDto) throws ApiException {
        TariffOptionDaoImpl tariffOptionDao = daoFactory.createTariffOptionDao();

        try {
            tariffOptionDao.create(tariffOptionCreateDto);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return true;
    }

    public boolean updateTariffOption(TariffOptionUpdateDto tariffOptionDto) throws ApiException {
        TariffOptionDaoImpl tariffOptionDao = daoFactory.createTariffOptionDao();

        try {
            tariffOptionDao.update(tariffOptionDto);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return true;
    }

    public boolean deleteTariffOption(TariffOptionDeleteDto tariffOptionDeleteDto) throws ApiException {
        TariffOptionDaoImpl tariffOptionDao = daoFactory.createTariffOptionDao();

        try {
            tariffOptionDao.delete(tariffOptionDeleteDto);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return true;
    }
}
