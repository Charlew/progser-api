package pl.progser.store;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.progser.domain.Exercise;
import pl.progser.domain.Training;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.time.LocalDate;

public final class PostgresStore implements TrainingStore {
    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private DataSource dataSource;
    private Gson gson = new Gson();
    private static final String INSERT_TRAINING = "insert into training(time, id, \"jsonData\") values (?, ?, cast(? as json))";
    private static final String INSERT_EXERCISE = "update training where id = ? set \"jsonData\" = \"jsonData\" || ?";
    private static final String GET_TRAINING_QUERY = "select \"jsonData\" from training where id =?";


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
    public void insertExercise(String trainingId, Exercise exercise) {
        try(var connection = dataSource.getConnection()) {
            try(var statement = connection.prepareStatement(INSERT_EXERCISE)) {
                statement.setObject(1, trainingId);
                statement.setObject(2, exercise);
                statement.execute();
            }
        } catch (SQLException e) {
            logger.error("Not stored");
        }
    }

    @Override
    public void storeTraining(Training training) {
        try(var connection = dataSource.getConnection()) {
            try(var statement = connection.prepareStatement(INSERT_TRAINING)) {
                statement.setObject(1, LocalDate.now());
                statement.setObject(2, training.getId());
                statement.setString(3, serializeJson(training));
                statement.execute();
            }
        } catch (SQLException e) {
            logger.error("Not stored");
        }
    }
}
