package pl.progser.store;

import pl.progser.model.Training;

public class StandardTrainingStore implements TrainingStore {
    private TrainingStore store;

    StandardTrainingStore(TrainingStore store) {
        this.store = store;
    }

    @Override
    public void storeData(Training training) {
        store.storeData(training);
    }
}
