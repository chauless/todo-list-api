package pet.tasktrackerapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
@Schema(description = "Information about User")
public class UserDto implements Serializable {

    private Long id;

    @Column(unique = true)
    private String username;

    private List<TaskDto> tasks;
}
