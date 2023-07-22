package pet.tasktrackerapi.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @GetMapping
    public ResponseEntity<String> getAllTasks() {
        return ResponseEntity.ok("All tasks");
    }
}
