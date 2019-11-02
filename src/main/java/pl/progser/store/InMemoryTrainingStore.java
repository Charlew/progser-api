package pl.progser.store;

import com.google.gson.Gson;
import pl.progser.domain.Training;
import pl.progser.utils.GsonTransformer;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryTrainingStore implements TrainingStore {

    private final Map<String, Training> trainingMap = new HashMap<>();

    private final GsonTransformer transformer = new GsonTransformer();

    @Override
    public void storeTraining(String training) {
        var trainingObject = transformer.deserialize(training);
        trainingMap.put(trainingObject.getId(), trainingObject);
    }

    @Override
    public String getTraining(String id) {
        return new Gson().toJson(trainingMap.get(id));
    }

    @Override
    public void insertExercise(String trainingId, String serializedExercise) {
    }
}
