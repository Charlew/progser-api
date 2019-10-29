package pl.progser.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.progser.domain.Training;
import pl.progser.store.TrainingStore;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(
        value = "/v1/progser",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class Endpoint {

    private final TrainingStore store;
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Endpoint(TrainingStore store) {
        this.store = store;
    }

    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    @PostMapping("/addTraining")
    public String addTraining(@RequestBody Training training) {
        store.storeTraining(training);
        logger.info("Training created with id = {}", training.getId());

        return training.getId();
    }

    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    @PostMapping("/addExercise")
    public HttpStatus addExercise(@RequestBody Training training) {

        return HttpStatus.NON_AUTHORITATIVE_INFORMATION;
    }
}
