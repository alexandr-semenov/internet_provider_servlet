package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.constants.SubscriptionStatus;
import ua.company.exception.DBException;
import ua.company.model.dto.user.UserIdDto;
import ua.company.model.entity.Account;
import ua.company.model.entity.Subscription;

import java.sql.*;

public class AccountDaoImpl implements AccountDao {
    private static final Logger LOGGER = Logger.getLogger(AccountDaoImpl.class);

    private static final String ID = "id";
    private static final String AMOUNT = "amount";

    private final Connection connection;
    private SubscriptionDaoImpl subscriptionDao;

    public AccountDaoImpl(Connection connection, SubscriptionDaoImpl subscriptionDao) {
        this.connection = connection;
        this.subscriptionDao = subscriptionDao;
    }

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

    public Account findByUser(Long userId) throws SQLException, DBException {
        Account account = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM account WHERE user_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = extractAccountFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("get_account_error");
        } finally {
            close(resultSet);
            close(preparedStatement);
        }

        return account;
    }

    public boolean makeDeposit(Account account, Double newAmount) throws DBException, SQLException {
        connection.setAutoCommit(false);

        return updateAccount(account, newAmount);
    }

    public boolean makeDebit(Account account, Double newAmount, Subscription subscription) throws DBException, SQLException {
        connection.setAutoCommit(false);

        subscriptionDao.updateStatus(subscription, SubscriptionStatus.ACTIVE);

        return updateAccount(account, newAmount);
    }

    private boolean updateAccount(Account account, Double newAmount) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = " UPDATE account SET amount = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, newAmount);
            preparedStatement.setLong(2, account.getId());

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("deposit_error");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return true;
    }

    @Override
    public Account findById(int id) {
        return null;
    }

    private Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong(ID));
        account.setAmount(resultSet.getDouble(AMOUNT));

        return account;
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
