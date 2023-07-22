package pet.tasktrackerapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class TaskDto implements Serializable {

    @NotNull
    private final UUID id;

    @NotBlank
    private final String title;

    @NotNull
    private final String description;

    private final Boolean completed;
}
