package pl.progser.store;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.progser.domain.Training;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.time.LocalDate;

public class PostgresStore implements TrainingStore {
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private DataSource dataSource;
    private Gson gson;
    private static final String INSERT_TRAINING_QUERY = "insert into training(time, \"jsonData\") values (?, cast(? as json))";

    public PostgresStore(String url, String password) {
        var pgDataSource = new PGSimpleDataSource();
        pgDataSource.setUrl(url);
        pgDataSource.setPassword(password);

        var hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(pgDataSource);

        dataSource = new HikariDataSource(hikariConfig);
    }

    private String serializeJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public void storeData(Training training) {
        try(var connection = dataSource.getConnection()) {
            try(var statement = connection.prepareStatement(INSERT_TRAINING_QUERY)) {
                statement.setObject(1, LocalDate.now());
                statement.setString(2, serializeJson(training));
            }
        } catch (SQLException e) {
            logger.error("Not stored");
        }
    }
}
