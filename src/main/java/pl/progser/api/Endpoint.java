package pl.progser.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.progser.domain.Exercise;
import pl.progser.domain.Training;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(
        value = "/v1/progser",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class Endpoint {

    private final TrainingHandler handler;

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Endpoint(TrainingHandler handler) {
        this.handler = handler;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addTraining")
    public String addTraining(@RequestBody Training training) {
        logger.info("Training created with id = {}", training.getId());
        handler.addTraining(training);
        return training.getId();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getTraining/{id}")
    public Training getTraining(@PathVariable String id) {
        return handler.getTraining(id);
    }

    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    @PostMapping("/addExercise/{trainingId}")
    public HttpStatus addExercise(@RequestBody Exercise exercise, @PathVariable String trainingId) {
        handler.addExercise(exercise, trainingId);
        return HttpStatus.NON_AUTHORITATIVE_INFORMATION;
    }
}
