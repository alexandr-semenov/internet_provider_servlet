package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.exception.DBException;
import ua.company.model.dto.product.TariffProductDto;
import ua.company.model.dto.tariff.TariffDto;
import ua.company.model.dto.tariff.TariffIdDto;
import ua.company.model.dto.tariff.TariffPriceDto;
import ua.company.model.entity.Tariff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TariffDaoImpl implements TariffDao {
    private static final Logger LOGGER = Logger.getLogger(TariffDaoImpl.class);

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";

    private static final String TARIFF_ID = "tariff_id";
    private static final String TARIFF_NAME = "tariff_name";
    private static final String PRODUCT_NAME = "product_name";

    private final Connection connection;
    private TariffOptionDaoImpl tariffOption = null;

    public TariffDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public TariffDaoImpl(Connection connection, TariffOptionDaoImpl tariffOption) {
        this.connection = connection;
        this.tariffOption = tariffOption;
    }

    public List<Tariff> findTariffsByProduct(Long id) throws DBException, SQLException {
        List<Tariff> tariffs = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM tariff WHERE product_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tariffs.add(extractTariffFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_not_found_exception");
        } finally {
            close(resultSet);
            close(preparedStatement);
        }

        return tariffs;
    }

    public List<TariffProductDto> findAllTariffWithProduct() throws DBException {
        List<TariffProductDto> tariffs = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT t.id tariff_id, t.name tariff_name, p.name product_name FROM tariff as t LEFT JOIN product as p ON t.product_id = p.id";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tariffs.add(extractTariffProductDtoFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("get_tariff_error");
        } finally {
            close(resultSet);
            close(connection);
        }

        return tariffs;
    }

    public List<TariffPriceDto> findMultipleTariffs(List<TariffIdDto> tariffIdDto) throws SQLException, DBException {
        List<TariffPriceDto> tariffs = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT price FROM tariff WHERE id IN (?)";

        String ids = tariffIdDto.stream().map(i -> String.valueOf(i.getId())).collect(Collectors.joining(",", "(", ")"));
        query = query.replace("(?)", ids);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tariffs.add(extractTariffPriceDtoFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_not_found_exception");
        } finally {
            close(resultSet);
            close(preparedStatement);
        }

        return tariffs;
    }

    public List<Tariff> findTariffsBySubscription(Long subscriptionId) throws SQLException, DBException {
        List<Tariff> tariffs = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT t.id, t.name, t.description, t.price FROM subscription s LEFT JOIN subscription_tariff st ON s.id = st.subscription_id LEFT JOIN tariff t ON st.tariff_id = t.id WHERE s.id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, subscriptionId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tariffs.add(extractTariffCabinetFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_not_found_exception");
        } finally {
            close(resultSet);
            close(preparedStatement);
        }

        return tariffs;
    }

    public Tariff findById(Long id) throws DBException {
        Tariff tariff = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM tariff WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                tariff = extractTariffFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_not_found_exception");
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return tariff;
    }

    public void update(TariffDto tariffDto) throws DBException {
        PreparedStatement preparedStatement = null;
        final String query = "UPDATE tariff SET name = ?, description = ?, price = ?, product_id = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tariffDto.getName());
            preparedStatement.setString(2, tariffDto.getDescription());
            preparedStatement.setDouble(3, tariffDto.getPrice());
            preparedStatement.setLong(4, tariffDto.getProductId());
            preparedStatement.setLong(5, tariffDto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_update_exception");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    private Tariff extractTariffCabinetFromResultSet(ResultSet resultSet) throws SQLException {
        Tariff tariffCabinet = new Tariff();
        tariffCabinet.setId(resultSet.getLong(ID));
        tariffCabinet.setName(resultSet.getString(NAME));
        tariffCabinet.setDescription(resultSet.getString(DESCRIPTION));
        tariffCabinet.setPrice(resultSet.getDouble(PRICE));

        return tariffCabinet;
    }

    private TariffPriceDto extractTariffPriceDtoFromResultSet(ResultSet resultSet) throws SQLException {
        TariffPriceDto tariffPriceDto = new TariffPriceDto();
        tariffPriceDto.setPrice(resultSet.getDouble(PRICE));

        return tariffPriceDto;
    }

    private Tariff extractTariffFromResultSet(ResultSet resultSet) throws SQLException, DBException {
        Tariff tariff = new Tariff();
        tariff.setId(resultSet.getLong(ID));
        tariff.setName(resultSet.getString(NAME));
        tariff.setDescription(resultSet.getString(DESCRIPTION));
        tariff.setPrice(resultSet.getDouble(PRICE));
        tariff.setOptions(tariffOption.findTariffsOptionsByTariff(resultSet.getLong(ID)));

        return tariff;
    }

    private TariffProductDto extractTariffProductDtoFromResultSet(ResultSet resultSet) throws SQLException {
        TariffProductDto tariffProductDto = new TariffProductDto();
        tariffProductDto.setTariffId(resultSet.getLong(TARIFF_ID));
        tariffProductDto.setTariffName(resultSet.getString(TARIFF_NAME));
        tariffProductDto.setProductName(resultSet.getString(PRODUCT_NAME));

        return tariffProductDto;
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
