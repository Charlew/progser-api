package pl.progser.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.AccessLevel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Training {

    @JsonIgnore
    private String id;

    @ApiModelProperty(example = "FBW")
    @NotNull(message = "required")
    private String name;

    @ApiModelProperty(example = "MONDAY")
    @NotNull(message = "required")
    private DayOfWeek dayOfWeek;

    @NotEmpty(message = "notEmpty")
    private List<Exercise> exercises;

    @ApiModelProperty(example = "2019-10-15")
    private LocalDate date;

    public Training() {
        id = UUID.randomUUID().toString();
    }
}
