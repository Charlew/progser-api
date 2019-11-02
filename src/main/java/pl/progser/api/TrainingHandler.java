package pl.progser.api;

import org.springframework.stereotype.Service;
import pl.progser.domain.Exercise;
import pl.progser.domain.Training;
import pl.progser.store.TrainingStore;
import pl.progser.utils.GsonTransformer;

@Service
public final class TrainingHandler {

    private final GsonTransformer transformer = new GsonTransformer();

    private final TrainingStore store;

    public TrainingHandler(TrainingStore store) {
        this.store = store;
    }

    void addTraining(Training training) {
        store.storeTraining(transformer.serialize(training));
    }

    Training getTraining(String id) {
        return transformer.deserialize(store.getTraining(id));
    }

    void addExercise(Exercise exercise, String trainingId) {
        store.insertExercise(trainingId, transformer.serialize(exercise));
    }
}
