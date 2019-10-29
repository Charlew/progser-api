package pl.progser.store;

import pl.progser.domain.Exercise;
import pl.progser.domain.Training;

public class StandardTrainingStore implements TrainingStore {
    private TrainingStore store;

    StandardTrainingStore(TrainingStore store) {
        this.store = store;
    }

    @Override
    public void storeTraining(Training training) {
        store.storeTraining(training);
    }

    @Override
    public String getTraining(String id) {
        return store.getTraining(id);
    }

    @Override
    public void insertExercise(String trainingId, Exercise exercise) {
        store.insertExercise(trainingId, exercise);
    }
}
