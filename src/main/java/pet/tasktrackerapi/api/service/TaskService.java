package pet.tasktrackerapi.api.service;

import pet.tasktrackerapi.api.dto.NewTaskRequest;
import pet.tasktrackerapi.api.dto.TaskDto;
import pet.tasktrackerapi.api.model.Task;
import pet.tasktrackerapi.api.model.User;

import java.util.List;

public interface TaskService {
    /**
     * Retrieves a list of tasks for a specific user.
     * The result is cached for performance optimization.
     *
     * @param user The user whose tasks are to be retrieved.
     * @return A list of TaskDto objects.
     */
    List<TaskDto> getUserTasks(User user);

    /**
     * Creates a new task for a specific user.
     * The cache is evicted after the operation to ensure data consistency.
     *
     * @param user The user for whom the task is to be created.
     * @param newTaskRequest The request object containing the task details.
     * @return The created Task object.
     */
    Task createTask(User user, NewTaskRequest newTaskRequest);

    /**
     * Deletes a task for a specific user.
     * The cache is evicted after the operation to ensure data consistency.
     *
     * @param user The user for whom the task is to be deleted.
     * @param id The id of the task to be deleted.
     */
    void deleteTask(User user, Long id);

    /**
     * Updates a task for a specific user.
     * The cache is evicted after the operation to ensure data consistency.
     *
     * @param user The user for whom the task is to be updated.
     * @param taskDto The DTO object containing the updated task details.
     * @return The updated Task object.
     */
    Task updateTask(User user, TaskDto taskDto);
}
