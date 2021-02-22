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

    private static final String ID = "id";
    private static final String PRICE = "price";
    private static final String STATUS = "status";

    private UserDaoImpl userDao;
    private AccountDaoImpl accountDao;
    private TariffDaoImpl tariffDao;
    private SubscriptionTariffDao subscriptionTariffDao;
    private final Connection connection;

    public SubscriptionDaoImpl(Connection connection) {

        this.connection = connection;
    }

    public SubscriptionDaoImpl(Connection connection, TariffDaoImpl tariffDao) {
        this.connection = connection;
        this.tariffDao = tariffDao;
    }

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

    public Subscription findByUserId(Long userId) throws SQLException, DBException {
        Subscription subscription = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM subscription WHERE user_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                subscription = extractSubscriptionFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("get_subscription_error");
        } finally {
            close(resultSet);
            close(preparedStatement);
        }

        return subscription;
    }

    public boolean updateStatus(Subscription subscription, String status) throws DBException, SQLException {
        PreparedStatement preparedStatement = null;
        final String query = " UPDATE subscription SET status = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, subscription.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("update_subscription_error");
        } finally {
            close(preparedStatement);
        }

        return true;
    }

    private Subscription extractSubscriptionFromResultSet(ResultSet resultSet) throws SQLException, DBException {
        Subscription subscription = new Subscription();
        subscription.setId(resultSet.getLong(ID));
        subscription.setPrice(resultSet.getDouble(PRICE));
        subscription.setStatus(Subscription.Status.valueOf(resultSet.getString(STATUS)));
        subscription.setTariffs(tariffDao.findTariffsBySubscription(resultSet.getLong(ID)));

        return subscription;
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
