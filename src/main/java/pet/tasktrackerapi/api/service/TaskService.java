package pet.tasktrackerapi.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pet.tasktrackerapi.api.dto.TaskDto;
import pet.tasktrackerapi.api.model.Task;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public List<TaskDto> getUserTasks(User user) {
        List<Task> tasks = taskRepository.getTasksByUser_Id(user.getId());
        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).toList();
    }

    public void deleteTask(User user, UUID uuid){
        if (taskRepository.existsByUserAndId(user, uuid)){
            taskRepository.deleteTaskById(uuid);
        } else {
            throw new NotFoundException("No task with such id");
        }
    }

    public UUID createTask(User user, TaskDto taskDto){
        Task newTask = Task
                .builder()
                .title(taskDto.getTitle())
                .details(taskDto.getDetails())
                .completed(false)
                .user(user)
                .build();
        return taskRepository.save(newTask).getId();
    }

    public UUID updateTask(User user, TaskDto taskDto) {
        if (taskRepository.existsByUserAndId(user, taskDto.getId())){
            Task task = Task
                    .builder()
                    .id(taskDto.getId())
                    .title(taskDto.getTitle())
                    .details(taskDto.getDetails())
                    .completed(taskDto.getCompleted())
                    .user(user)
                    .build();
            return taskRepository.save(task).getId();
        } else {
            throw new NotFoundException("No task with such id");
        }
    }

    public int countNonCompletedTasks(User user) {
        return taskRepository.countTasksByUserAndCompletedIsFalse(user.getId());
    }
}
