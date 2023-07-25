package pet.tasktrackerapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Getter
@Setter
@Schema(description = "Information about new Task")
public class NewTaskRequest implements Serializable {

    @NotBlank
    private String title;

    @NotNull
    private String details;
}