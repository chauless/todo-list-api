package pet.tasktrackerapi.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for registering a new user")
public class RegisterRequest {

    @NotBlank
    @Schema(description = "The username of the new user", example = "test")
    private String username;

    @NotBlank
    @Schema(description = "Password", example = "test")
    private String password;
}