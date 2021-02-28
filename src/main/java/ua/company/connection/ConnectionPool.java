package ua.company.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool connectionPool = null;

    public ConnectionPool() {
    }

    public Connection getConnection() {
        Context context;
        Connection connection = null;

        try {
            context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql");
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return connection;
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }
}
