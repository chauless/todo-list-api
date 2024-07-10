package pet.tasktrackerapi.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO for registering a new user")
public class RegisterRequest {

    @NotBlank
    @Schema(description = "The username of the new user", example = "test")
    private String username;

    @NotBlank
    @Schema(description = "Password", example = "test")
    private String password;

    // Приватный конструктор, чтобы предотвратить создание объекта напрямую
    private RegisterRequest(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }

    // Публичные геттеры
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Статический класс Builder
    public static class Builder {
        private String username;
        private String password;

        public Builder() {}

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterRequest build() {
            return new RegisterRequest(this);
        }
    }
}