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
@Schema(description = "DTO for authentication request")
public class AuthenticationRequest {

    @NotBlank
    @Schema(description = "The username", example = "test")
    private String username;

    @NotBlank
    @Schema(description = "The password, BCrypt encoded", example = "test")
    private String password;
}