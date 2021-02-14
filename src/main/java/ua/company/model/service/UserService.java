package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.UserDaoImpl;
import ua.company.model.entity.User;

public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public User getUserByUsername(String username) throws ApiException {
        UserDaoImpl userDao = daoFactory.createUserDao();
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new ApiException("Couldn't find username");
        }

        return user;
    }
}
