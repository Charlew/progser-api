package pl.progser.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.progser.domain.Training;

public final class GsonTransformer {

    private Gson gson;

    public GsonTransformer() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

    public String serialize(Object object) {
        return gson.toJson(object);
    }

    public Training deserialize(String json) {
        return new Gson().fromJson(json, Training.class);
    }
}
