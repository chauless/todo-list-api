package pet.tasktrackerapi.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserDto implements Serializable {

    private Long id;

    private String username;

    private List<TaskDto> tasks;
}
