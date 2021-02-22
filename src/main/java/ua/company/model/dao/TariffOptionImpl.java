package ua.company.model.dao;

import org.apache.log4j.Logger;
import ua.company.exception.DBException;
import ua.company.model.entity.TariffOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TariffOptionImpl implements TariffOptionDao {
    private static final Logger LOGGER = Logger.getLogger(TariffOptionImpl.class);

    private static final String ID = "id";
    private static final String ITEM = "item";

    private final Connection connection;

    public TariffOptionImpl(Connection connection) {
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
