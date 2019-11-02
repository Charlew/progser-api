package pl.progser.store;

public interface TrainingStore {
    void storeTraining(String training);
    String getTraining(String id);
    void insertExercise(String trainingId, String serializedExercise);
}