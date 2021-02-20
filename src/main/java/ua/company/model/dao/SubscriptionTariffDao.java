package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubscriptionTariffDao {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionTariffDao.class);
    private final Connection connection;

    public SubscriptionTariffDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createSubscriptionTariff(Long subscriptionId, Long tariffId) throws DBException, SQLException {
        PreparedStatement preparedStatement = null;
        final String query = "INSERT INTO subscription_tariff (subscription_id, tariff_id) VALUES(?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, subscriptionId);
            preparedStatement.setLong(2, tariffId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("create_subscription_tariff_error");
        } finally {
            close(preparedStatement);
        }

        return true;
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
}
