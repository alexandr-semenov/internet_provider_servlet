package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.model.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    private static final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class);

    private static final String ID = "id";
    private static final String NAME = "name";

    private final Connection connection;

    public RoleDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Role entity) {

    }

    @Override
    public Role findById(int id) {
        Role role = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

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
        }

        return role;
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public void update(Role entity) {

    }

    @Override
    public void delete(int id) {

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

    private Role extractRoleFromResultSet(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong(ID));
        role.setName(resultSet.getString(NAME));

        return role;
    }
}
