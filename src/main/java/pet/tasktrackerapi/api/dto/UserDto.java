package pet.tasktrackerapi.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {

    private final Long id;

    private final String username;

    private final List<TaskDto> tasks;
}
