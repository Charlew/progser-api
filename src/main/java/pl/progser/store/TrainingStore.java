package pl.progser.store;

import pl.progser.model.Training;

public interface TrainingStore {
    void storeData(Training training);

    class Fakes {
        public static TrainingStore dummy() {
            return new TrainingStore() {

                @Override
                public void storeData(Training training) {

                }
            };
        }
    }
}