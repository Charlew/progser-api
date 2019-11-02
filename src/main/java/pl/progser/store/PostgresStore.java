package pl.progser.store;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.time.LocalDate;

public final class PostgresStore implements TrainingStore {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private DataSource dataSource;

    private static final String INSERT_TRAINING = "insert into training(time, \"jsonData\") values (?, ?, cast(? as json))";

    private static final String INSERT_EXERCISE = "update training where id = ? set \"jsonData\" = \"jsonData\" || ?";

    private static final String GET_TRAINING_QUERY = "select \"jsonData\" from training where id =? and \"jsonData\" ->> 'id' = ?";

    public PostgresStore(String url, String password) {
        var pgDataSource = new PGSimpleDataSource();
        pgDataSource.setUrl(url);
        pgDataSource.setPassword(password);

        var hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(pgDataSource);

        dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public String getTraining(String id) {
        try(var connection = dataSource.getConnection()) {
            try(var statement = connection.prepareStatement(GET_TRAINING_QUERY)) {
                statement.setObject(1, id);

                var rs = statement.executeQuery();

                rs.next();

                return rs.getString("jsonData");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertExercise(String trainingId, String serializedExercise) {
        try(var connection = dataSource.getConnection()) {
            try(var statement = connection.prepareStatement(INSERT_EXERCISE)) {
                statement.setObject(1, trainingId);
                statement.setString(2, serializedExercise);
                statement.execute();
            }
        } catch (SQLException e) {
            logger.error("Not stored");
        }
    }

    @Override
    public void storeTraining(String training) {
        try(var connection = dataSource.getConnection()) {
            try(var statement = connection.prepareStatement(INSERT_TRAINING)) {
                statement.setObject(1, LocalDate.now());
                statement.setString(2, training);
                statement.execute();
            }
        } catch (SQLException e) {
            logger.error("Not stored");
        }
    }
}
