package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.UserDaoImpl;
import ua.company.model.dto.user.UserDto;
import ua.company.model.entity.User;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public User getUserByUsername(String username) throws ApiException {
        UserDaoImpl userDao = daoFactory.createUserDao();
        User user;

        try {
            user = userDao.findByUsername(username);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return user;
    }

    public List<UserDto> getNotActiveUsersPaginated(int size, int offset) throws ApiException {
        UserDaoImpl userDao = daoFactory.createUserDao();
        List<UserDto> users;

        try {
            users = userDao.findNotActivePaginatedUsers(size, offset);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_NOT_FOUND);
        }

        return users;
    }

    public int getTotalNotActiveUsers() throws ApiException {
        UserDaoImpl userDao = daoFactory.createUserDao();

        try {
            return userDao.findTotalNotActiveUsers();
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public boolean activateUserById(Long id) throws ApiException {
        UserDaoImpl userDao = daoFactory.createUserDao();

        try {
            return userDao.updateActiveById(id);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
