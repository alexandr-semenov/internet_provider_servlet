package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.exception.DBException;
import ua.company.model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao{
    private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class);

    private static final String ID = "id";
    private static final String NAME = "name";

    private final Connection connection;
    private final TariffDaoImpl tariffDao;

    public ProductDaoImpl(Connection connection, TariffDaoImpl tariffDao) {
        this.connection = connection;
        this.tariffDao = tariffDao;
    }

    public Product findByName(String name) throws DBException, SQLException {
        Product product = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM product WHERE name = ?";

        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = extractProductFromResultSet(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("product_not_found_exception");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return product;
    }

    public Product findById(Long id) throws SQLException, DBException {
        Product product = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM product WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = extractProductFromResultSet(resultSet);
            }
        } catch (SQLException | DBException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("product_not_found_exception");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return product;
    }

    public List<Product> findAll() throws DBException {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM product";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                products.add(extractProductFromResultSet(resultSet));
            }
        } catch (SQLException | DBException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("product_not_found_exception");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }


        return products;
    }

    private Product extractProductFromResultSet(ResultSet resultSet) throws SQLException, DBException {
        return Product.builder()
                .setId(resultSet.getLong(ID))
                .setName(resultSet.getString(NAME))
                .setTariffs(tariffDao.findTariffsByProduct(resultSet.getLong(ID)))
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
