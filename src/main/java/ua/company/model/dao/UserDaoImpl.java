package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.constants.RoleName;
import ua.company.exception.ApiException;
import ua.company.exception.DBException;
import ua.company.model.dto.SubscriptionDto;
import ua.company.model.dto.user.UserDto;
import ua.company.model.dto.user.UserIdDto;
import ua.company.model.entity.Role;
import ua.company.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ACTIVE = "active";
    private static final String ROLE_ID = "role_id";
    private static final String TOTAL = "total";

    private final Connection connection;
    private final RoleDaoImpl roleDao;
    private SubscriptionDaoImpl subscriptionDao = null;
    private AccountDaoImpl accountDao;

    public UserDaoImpl(Connection connection, RoleDaoImpl roleDao) {
        this.connection = connection;
        this.roleDao = roleDao;
    }

    public UserDaoImpl(Connection connection, RoleDaoImpl roleDao, SubscriptionDaoImpl subscriptionDao, AccountDaoImpl accountDao) {
        this.connection = connection;
        this.roleDao = roleDao;
        this.subscriptionDao = subscriptionDao;
        this.accountDao = accountDao;
    }

    public User findByUsername(String username) throws DBException {
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM user WHERE username = ?";

        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = extractUserFromResultSet(resultSet);
            }

            connection.commit();
        } catch (SQLException | ApiException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("get_user_error");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return user;
    }

    public List<UserDto> findNotActivePaginatedUsers(int size, int offset) throws DBException {
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
            throw new DBException("get_not_active_user_errors");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return users;
    }

    public int findTotalNotActiveUsers() throws DBException {
        int total = 0;
        PreparedStatement preparedStatement = null;
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
            throw new DBException("total_users_error");
        } finally {
            close(resultSet);
            close(preparedStatement);
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

    public UserIdDto create(SubscriptionDto subscriptionDto) throws DBException, SQLException {
        UserIdDto userIdDto = null;
        Role role = roleDao.findByName(RoleName.ROLE_USER);

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "INSERT INTO user (username, password, active, role_id) VALUES(?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, subscriptionDto.getUsername());
            preparedStatement.setString(2, subscriptionDto.getPassword());
            preparedStatement.setInt(3, 0);
            preparedStatement.setLong(4, role.getId());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                userIdDto = extractUserIdDtoFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("create_user_error");
        } finally {
            close(resultSet);
            close(preparedStatement);
        }

        return userIdDto;
    }

    private UserIdDto extractUserIdDtoFromResultSet(ResultSet resultSet) throws SQLException {
        UserIdDto userIdDto = new UserIdDto();
        userIdDto.setId(resultSet.getLong(1));

        return userIdDto;
    }

    private UserDto extractUserDtoFromResultSet(ResultSet resultSet) throws SQLException {
        UserDto user = new UserDto();
        user.setId(resultSet.getLong(ID));
        user.setUsername(resultSet.getString(USERNAME));
        user.setActive(resultSet.getBoolean(ACTIVE));

        return user;
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException, ApiException, DBException {
        return User.builder()
                .setId(resultSet.getLong(ID))
                .setUsername(resultSet.getString(USERNAME))
                .setPassword(resultSet.getString(PASSWORD))
                .setActive(resultSet.getBoolean(ACTIVE))
                .setRole(roleDao.findById(resultSet.getInt(ROLE_ID)))
                .setSubscription(subscriptionDao.findByUserId(resultSet.getLong(ID)))
                .setAccount(accountDao.findByUser(resultSet.getLong(ID)))
                .build();
    }

    @Override
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
}
