package pl.progser.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import javax.validation.constraints.NotNull;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Exercise {

    @ApiModelProperty(example = "Bench press")
    @NotNull(message = "required")
    private String name;

    @ApiModelProperty(example = "3")
    private int sets;

    @ApiModelProperty(example = "5")
    private int repetitions;

    @ApiModelProperty(example = "100")
    private int weightInKg;
}
