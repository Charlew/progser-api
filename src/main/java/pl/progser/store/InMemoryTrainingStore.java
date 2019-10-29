package pl.progser.store;

import com.google.gson.Gson;
import pl.progser.domain.Exercise;
import pl.progser.domain.Training;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryTrainingStore implements TrainingStore {

    private final Map<String, Training> trainingMap = new HashMap<>();

    @Override
    public void storeTraining(Training training) {
        trainingMap.put(training.getId(), training);
    }

    @Override
    public String getTraining(String id) {
        return new Gson().toJson(trainingMap.get(id));
    }

    @Override
    public void insertExercise(String trainingId, Exercise exercise) {
    }
}
