package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.exception.ApiException;
import ua.company.exception.DBException;
import ua.company.model.dto.user.UserDto;
import ua.company.model.entity.User;
import ua.company.model.service.RoleService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private final RoleService roleService;

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ACTIVE = "active";
    private static final String ROLE_ID = "role_id";
    private static final String TOTAL = "total";

    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
        this.roleService = new RoleService();
    }

    public User findByUsername(String username) {
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM user WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = extractUserFromResultSet(resultSet);
            }

        } catch (SQLException | ApiException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return user;
    }

    public List<UserDto> findNotActivePaginatedUsers(int size, int offset) {
        List<UserDto> users = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String query = "SELECT id, username, active FROM user WHERE active = 0 LIMIT ? OFFSET ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, offset);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(extractUserDtoFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return users;
    }

    public int findTotalNotActiveUsers() {
        int total = 0;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;

        final String query = "SELECT COUNT(id) total FROM user WHERE active = 0";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                total = resultSet.getInt(TOTAL);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(resultSet);
            close(connection);
        }

        return total;
    }

    public boolean updateActiveById(Long id) throws DBException {
        PreparedStatement preparedStatement = null;
        final String query = "UPDATE user SET active = 1 WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            return !preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("activate_user_error");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    public void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                throw new IllegalStateException("Cannot close " + autoCloseable);
            }
        }
    }

    private UserDto extractUserDtoFromResultSet(ResultSet resultSet) throws SQLException {
        UserDto user = new UserDto();
        user.setId(resultSet.getLong(ID));
        user.setUsername(resultSet.getString(USERNAME));
        user.setActive(resultSet.getBoolean(ACTIVE));

        return user;
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException, ApiException {
        User user = new User();
        user.setId(resultSet.getLong(ID));
        user.setUsername(resultSet.getString(USERNAME));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setActive(resultSet.getBoolean(ACTIVE));
        user.setRole(roleService.getRoleById(resultSet.getInt(ROLE_ID)));

        return user;
    }
}
