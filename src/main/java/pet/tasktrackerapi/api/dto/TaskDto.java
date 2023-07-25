package pet.tasktrackerapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@Schema(description = "Information about Task")
public class TaskDto implements Serializable {

    private UUID id;

    @NotBlank
    private String title;

    @NotNull
    private String details;

    private Boolean completed;
}
