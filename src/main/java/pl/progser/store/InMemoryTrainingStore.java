package pl.progser.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.progser.domain.Training;
import pl.progser.utils.Transformer;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryTrainingStore implements TrainingStore {

    private final Map<String, Training> trainingMap = new HashMap<>();

    private final Transformer transformer = new Transformer();

    @Override
    public void storeTraining(String training) {
        var trainingObject = transformer.deserialize(training, Training.class);
        trainingMap.put(trainingObject.getId(), trainingObject);
    }

    @Override
    public String getTraining(String id) {
        try {
            return new ObjectMapper().writeValueAsString(trainingMap.get(id));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertExercise(String trainingId, String serializedExercise) {
    }
}
