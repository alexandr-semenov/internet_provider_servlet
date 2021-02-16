package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.UserDaoImpl;
import ua.company.model.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public User getUserByUsername(String username) throws ApiException {
        UserDaoImpl userDao = daoFactory.createUserDao();
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new ApiException(Arrays.asList("username_and_password_not_found_error"), HttpServletResponse.SC_NOT_FOUND);
        }

        return user;
    }
}
