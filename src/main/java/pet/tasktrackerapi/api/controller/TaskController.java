package pet.tasktrackerapi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pet.tasktrackerapi.api.dto.NewTaskRequest;
import pet.tasktrackerapi.api.dto.TaskDto;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.api.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks management", description = "Methods for managing tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(description = "Getting list of tasks")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<TaskDto>> getTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getUserTasks(user));
    }

    @PostMapping
    @Operation(description = "Creating new task")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UUID> createTask(@AuthenticationPrincipal User user,
                                           @RequestBody @Valid NewTaskRequest newTaskRequest) {
        UUID taskId = taskService.createTask(user, newTaskRequest);
        return ResponseEntity.ok(taskId);
    }

    @PutMapping(path = "/{uuid}")
    @Operation(description = "Updating task")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UUID> updateTask(@AuthenticationPrincipal User user,
                                           @RequestBody @Valid TaskDto taskDto) {
        UUID taskId = taskService.updateTask(user, taskDto);
        return ResponseEntity.ok(taskId);
    }

    @DeleteMapping(path = "/{uuid}")
    @Operation(description = "Deleting task by id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UUID> deleteTask(@AuthenticationPrincipal User user,
                                           @PathVariable UUID uuid) {
        taskService.deleteTask(user, uuid);
        return ResponseEntity.ok(uuid);
    }
}
