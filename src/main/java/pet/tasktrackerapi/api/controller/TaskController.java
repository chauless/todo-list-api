package pet.tasktrackerapi.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pet.tasktrackerapi.api.dto.TaskDto;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.api.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getUserTasks(user));
    }

    @PostMapping
    public ResponseEntity<UUID> createTask(@AuthenticationPrincipal User user, @RequestBody @Valid TaskDto taskDto) {
        UUID taskId = taskService.createTask(user, taskDto);
        return ResponseEntity.ok(taskId);
    }

    @PutMapping(path = "/{uuid}")
    public ResponseEntity<UUID> updateTask(@AuthenticationPrincipal User user, @RequestBody @Valid TaskDto taskDto) {
        UUID taskId = taskService.updateTask(user, taskDto);
        return ResponseEntity.ok(taskId);
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<UUID> deleteTask(@AuthenticationPrincipal User user, @PathVariable UUID uuid) {
        taskService.deleteTask(user, uuid);
        return ResponseEntity.ok(uuid);
    }
}
