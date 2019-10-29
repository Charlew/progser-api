package pl.progser.store;

import pl.progser.domain.Exercise;
import pl.progser.domain.Training;

public interface TrainingStore {
    void storeTraining(Training training);
    String getTraining(String id);
    void insertExercise(String trainingId, Exercise exercise);
}