package pl.progser.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainingStoreConfiguration {

    @Bean
    TrainingStore trainingStore(@Value("${db.url:}") String url, @Value("${db.password:}") String password) {
        if (url.isBlank()) {
            return new StandardTrainingStore(new InMemoryTrainingStore());
        }
        return new StandardTrainingStore(new PostgresStore(url, password));
    }
}
