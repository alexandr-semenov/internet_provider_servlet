package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.exception.DBException;
import ua.company.model.dto.tariff.TariffOptionCreateDto;
import ua.company.model.dto.tariff.TariffOptionDeleteDto;
import ua.company.model.dto.tariff.TariffOptionUpdateDto;
import ua.company.model.entity.TariffOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TariffOptionDaoImpl implements TariffOptionDao {
    private static final Logger LOGGER = Logger.getLogger(TariffOptionDaoImpl.class);

    private static final String ID = "id";
    private static final String ITEM = "item";

    private final Connection connection;

    public TariffOptionDaoImpl(Connection connection) {
        this.connection = connection;
    }

    List<TariffOption> findTariffsOptionsByTariff(Long id) throws SQLException, DBException {
        List<TariffOption> tariffsOptions = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "SELECT * FROM tariff_option WHERE tariff_id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                tariffsOptions.add(extractTariffOptionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_option_not_found_exception");
        } finally {
            close(resultSet);
            close(preparedStatement);
        }

        return tariffsOptions;
    }

    public void create(TariffOptionCreateDto tariffOptionCreateDto) throws DBException {
        PreparedStatement preparedStatement = null;
        final String query = "INSERT INTO tariff_option (item, tariff_id) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tariffOptionCreateDto.getOption());
            preparedStatement.setLong(2, tariffOptionCreateDto.getTariffId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_option_update_exception");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }


    public void update(TariffOptionUpdateDto tariffOptionDto) throws DBException {
        PreparedStatement preparedStatement = null;
        final String query = "UPDATE tariff_option SET item = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tariffOptionDto.getOption());
            preparedStatement.setLong(2, tariffOptionDto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_option_update_exception");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    public void delete(TariffOptionDeleteDto tariffOptionDeleteDto) throws DBException {
        PreparedStatement preparedStatement = null;
        final String query = "DELETE FROM tariff_option WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, tariffOptionDeleteDto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("tariff_option_update_exception");
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    private TariffOption extractTariffOptionFromResultSet(ResultSet resultSet) throws SQLException {
        TariffOption tariffOption = new TariffOption();
        tariffOption.setId(resultSet.getLong(ID));
        tariffOption.setItem(resultSet.getString(ITEM));

        return tariffOption;
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
