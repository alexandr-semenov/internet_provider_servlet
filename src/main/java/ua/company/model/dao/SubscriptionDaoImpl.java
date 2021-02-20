package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.constants.SubscriptionStatus;
import ua.company.exception.DBException;
import ua.company.model.dto.SubscriptionDto;
import ua.company.model.dto.tariff.TariffIdDto;
import ua.company.model.dto.tariff.TariffPriceDto;
import ua.company.model.dto.user.UserIdDto;
import ua.company.model.entity.Subscription;

import java.sql.*;
import java.util.List;

public class SubscriptionDaoImpl implements SubscriptionDao {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionDaoImpl.class);
    private final UserDaoImpl userDao;
    private final AccountDaoImpl accountDao;
    private final TariffDaoImpl tariffDao;
    private SubscriptionTariffDao subscriptionTariffDao;
    private final Connection connection;

    public SubscriptionDaoImpl(
            UserDaoImpl userDao,
            AccountDaoImpl accountDao,
            TariffDaoImpl tariffDao,
            SubscriptionTariffDao subscriptionTariffDao,
            Connection connection
    ) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.tariffDao = tariffDao;
        this.subscriptionTariffDao = subscriptionTariffDao;
        this.connection = connection;
    }

    public boolean createSubscription(SubscriptionDto subscriptionDto) throws DBException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection.setAutoCommit(false);
        final String query = "INSERT INTO subscription (user_id, price, status) VALUES(?, ?, ?)";

        UserIdDto userIdDto = userDao.create(subscriptionDto);
        accountDao.create(userIdDto);

        List<TariffPriceDto> tariffs = tariffDao.findMultipleTariffs(subscriptionDto.getTariffs());
        Double totalPrice = tariffs.stream().map(TariffPriceDto::getPrice).reduce(0.0, Double::sum);

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, userIdDto.getId());
            preparedStatement.setDouble(2, totalPrice);
            preparedStatement.setString(3, SubscriptionStatus.NOT_ACTIVE);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                Long subscriptionId = resultSet.getLong(1);
                for (TariffIdDto tariffId : subscriptionDto.getTariffs()) {
                    subscriptionTariffDao.createSubscriptionTariff(subscriptionId, tariffId.getId());
                }
            }

            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("create_subscription_error");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return true;
    }

    @Override
    public Subscription findById(int id) {
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
