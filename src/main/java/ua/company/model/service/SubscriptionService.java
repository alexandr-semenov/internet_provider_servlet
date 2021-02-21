package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.SubscriptionDaoImpl;
import ua.company.model.dto.SubscriptionDto;
import ua.company.model.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class SubscriptionService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final UserService userService;

    public SubscriptionService(UserService userService) {
        this.userService = userService;
    }

    public boolean subscribe(SubscriptionDto subscriptionDto) throws ApiException {
        SubscriptionDaoImpl subscriptionDao = daoFactory.createSubscriptionDao();

        User user = userService.getUserByUsername(subscriptionDto.getUsername());

        if (user != null) {
            throw new ApiException(Arrays.asList("user_exist_exception"), HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            subscriptionDao.createSubscription(subscriptionDto);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return true;
    }
}
