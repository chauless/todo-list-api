package pet.tasktrackerapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Information about new Task")
public class NewTaskRequest implements Serializable {

    @NotBlank
    private final String title;

    @NotNull
    private final String details;
}