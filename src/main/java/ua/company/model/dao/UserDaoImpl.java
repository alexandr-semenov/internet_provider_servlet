package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ACTIVE = "active";

    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
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

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
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

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(ID));
        user.setUsername(resultSet.getString(USERNAME));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setActive(resultSet.getBoolean(ACTIVE));

        return user;
    }
}
