package ua.company.model.dao;

import org.apache.log4j.Logger;

import ua.company.exception.DBException;
import ua.company.model.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDaoImpl implements RoleDao {
    private static final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class);
    private static final String ID = "id";
    private static final String NAME = "name";
    private final Connection connection;

    public RoleDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Role findById(int id) {
        Role role = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM role WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                role = extractRoleFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(preparedStatement);
            close(resultSet);
        }

        return role;
    }

    public Role findByName(String name) throws SQLException, DBException {
        Role role = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM role WHERE name = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                role = extractRoleFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("get_role_error");
        } finally {
            close(preparedStatement);
            close(resultSet);
        }

        return role;
    }

    private Role extractRoleFromResultSet(ResultSet resultSet) throws SQLException {
        return Role.builder()
                .setId(resultSet.getLong(ID))
                .setName(resultSet.getString(NAME))
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
