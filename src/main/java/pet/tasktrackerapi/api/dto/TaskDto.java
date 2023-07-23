package pet.tasktrackerapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class TaskDto implements Serializable {

    private UUID id;

    @NotBlank
    private String title;

    @NotNull
    private String details;

    private Boolean completed;
}
