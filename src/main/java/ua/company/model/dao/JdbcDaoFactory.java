package ua.company.model.dao;

import ua.company.connection.ConnectionPool;

import java.sql.Connection;

public class JdbcDaoFactory extends DaoFactory {
    public UserDaoImpl createUserDao() {
        return new UserDaoImpl(getConnection());
    }

    private Connection getConnection() {
        Connection connection = ConnectionPool.getInstance().getConnection();
        if (connection == null) {
            throw new RuntimeException("Couldn't get connection");
        }

        return connection;
    }
}
