package pet.tasktrackerapi.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pet.tasktrackerapi.api.dto.NewTaskRequest;
import pet.tasktrackerapi.api.model.Task;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.exception.NotFoundException;
import pet.tasktrackerapi.repository.TaskRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper modelMapper;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, modelMapper);
    }

    @Test
    void testCreateTask() {

        User user = new User();
        NewTaskRequest newTaskRequest = new NewTaskRequest();
        newTaskRequest.setTitle("Test Title");
        newTaskRequest.setDetails("Test Details");

        Task task = new Task();
        UUID taskId = UUID.randomUUID();
        task.setId(taskId);

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        UUID result = taskService.createTask(user, newTaskRequest);

        assertEquals(taskId, result);
    }

    @Test
    void testDeleteTaskIfNotExists() {
        User user = new User();
        user.setId(1L);

        UUID taskId = UUID.randomUUID();

        when(taskRepository.existsByUserAndId(user, taskId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> taskService.deleteTask(user, taskId));
        verify(taskRepository, never()).deleteTaskById(taskId);
    }

    @Test
    void testDeleteTaskIfExists() {
        User user = new User();
        user.setId(1L);

        UUID taskId = UUID.randomUUID();

        when(taskRepository.existsByUserAndId(user, taskId)).thenReturn(true);
        taskService.deleteTask(user, taskId);

        verify(taskRepository, times(1)).deleteTaskById(taskId);
    }
}
