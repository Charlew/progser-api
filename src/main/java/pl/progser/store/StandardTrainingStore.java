package pl.progser.store;

public class StandardTrainingStore implements TrainingStore {
    private TrainingStore store;

    StandardTrainingStore(TrainingStore store) {
        this.store = store;
    }

    @Override
    public void storeTraining(String training) {
        store.storeTraining(training);
    }

    @Override
    public String getTraining(String id) {
        return store.getTraining(id);
    }

    @Override
    public void insertExercise(String trainingId, String exercise) {
        store.insertExercise(trainingId, exercise);
    }
}
