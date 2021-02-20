package ua.company.model.dao;

import ua.company.connection.ConnectionPool;
import ua.company.exception.ApiException;
import ua.company.model.service.RoleService;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Arrays;

public class JdbcDaoFactory extends DaoFactory {
    public UserDaoImpl createUserDao() throws ApiException {
        return new UserDaoImpl(getConnection(), new RoleService());
    }

    public RoleDaoImpl createRoleDao() throws ApiException {
        return new RoleDaoImpl(getConnection());
    }

    public ProductDaoImpl createProductDao() throws ApiException {
        Connection connection = getConnection();

        return new ProductDaoImpl(connection, new TariffDaoImpl(connection, new TariffOptionImpl(connection)));
    }

    @Override
    public TariffDaoImpl createTariffDao() throws ApiException {
        Connection connection = getConnection();

        return new TariffDaoImpl(connection, new TariffOptionImpl(connection));
    }

    @Override
    public SubscriptionDaoImpl createSubscriptionDao() throws ApiException {
        Connection connection = getConnection();

        return new SubscriptionDaoImpl(
                new UserDaoImpl(connection, new RoleDaoImpl(connection)),
                new AccountDaoImpl(connection),
                new TariffDaoImpl(connection),
                new SubscriptionTariffDao(connection),
                connection
        );
    }

    private Connection getConnection() throws ApiException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        if (connection == null) {
            throw new ApiException(Arrays.asList("db_connection_error"), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return connection;
    }
}
