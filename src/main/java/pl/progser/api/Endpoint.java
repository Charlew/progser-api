package pl.progser.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.progser.domain.Training;
import pl.progser.store.TrainingStore;

@RestController
@RequestMapping(
        value = "/v1/progser",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class Endpoint {

    private final TrainingStore store;

    public Endpoint(TrainingStore store) {
        this.store = store;
    }

    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    @PostMapping("/addTraining")
    public HttpStatus addTraining(@RequestBody Training training) {
        store.storeData(training);
        return HttpStatus.NON_AUTHORITATIVE_INFORMATION;
    }
}
