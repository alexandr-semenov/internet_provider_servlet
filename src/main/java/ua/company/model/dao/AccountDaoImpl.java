package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.exception.DBException;
import ua.company.model.dto.user.UserIdDto;
import ua.company.model.entity.Account;

import java.sql.*;

public class AccountDaoImpl implements AccountDao {
    private static final Logger LOGGER = Logger.getLogger(AccountDaoImpl.class);
    private Connection connection;

    public AccountDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean create(UserIdDto userIdDto) throws SQLException, DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "INSERT INTO account (amount, user_id) VALUES(?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, 0);
            preparedStatement.setLong(2, userIdDto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("account_create_error");
        } finally {
            close(resultSet);
            close(preparedStatement);
        }

        return true;
    }

    @Override
    public Account findById(int id) {
        return null;
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
