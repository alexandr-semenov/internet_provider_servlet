package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.SubscriptionDaoImpl;
import ua.company.model.dto.SubscriptionDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class SubscriptionService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public boolean subscribe(SubscriptionDto subscriptionDto) throws ApiException {
        SubscriptionDaoImpl subscriptionDao = daoFactory.createSubscriptionDao();
        try {
            subscriptionDao.createSubscription(subscriptionDto);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return true;
    }
}
