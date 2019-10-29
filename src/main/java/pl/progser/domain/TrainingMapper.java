package pl.progser.domain;

import org.springframework.stereotype.Service;
import pl.progser.store.TrainingStore;

@Service
final class TrainingMapper {

    private final TrainingStore store;

    TrainingMapper(TrainingStore store) {
        this.store = store;
    }



}
