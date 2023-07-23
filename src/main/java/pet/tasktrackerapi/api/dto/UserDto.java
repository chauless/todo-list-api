package pet.tasktrackerapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "Information about User")
public class UserDto implements Serializable {

    private Long id;

    private String username;

    private List<TaskDto> tasks;
}
