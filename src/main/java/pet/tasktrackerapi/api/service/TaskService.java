package pet.tasktrackerapi.api.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.tasktrackerapi.api.dto.NewTaskRequest;
import pet.tasktrackerapi.api.dto.TaskDto;
import pet.tasktrackerapi.api.model.Task;
import pet.tasktrackerapi.api.model.User;
import pet.tasktrackerapi.exception.NotFoundException;
import pet.tasktrackerapi.repository.TaskRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public UUID createTask(User user, NewTaskRequest newTaskRequest){
        Task newTask = Task
                .builder()
                .title(newTaskRequest.getTitle())
                .details(newTaskRequest.getDetails())
                .completed(false)
                .user(user)
                .build();
        return taskRepository.save(newTask).getId();
    }

    @Transactional
    public void deleteTask(User user, UUID uuid){
        if (taskRepository.existsByUserAndId(user, uuid)){
            taskRepository.deleteTaskById(uuid);
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional
    public void updateTask(User user, TaskDto taskDto) {
        if (!taskRepository.existsByUserAndId(user, taskDto.getId())){
            throw new NotFoundException();
        }

        Task task = taskRepository.findById(taskDto.getId()).get();
        if ((taskDto.getCompleted() && !task.getCompleted())){
            completeTask(taskDto);
        } else if (taskDto.getCompleted()){
            updateCompletedTask(taskDto);
        } else {
            updateUncompletedTask(taskDto);
        }
    }

    protected void updateUncompletedTask(TaskDto task){
        taskRepository.update(task.getId(), task.getTitle(), task.getDetails(), task.getCompleted(), null);
    }

    protected void completeTask(TaskDto task){
        taskRepository.update(task.getId(), task.getTitle(), task.getDetails(), task.getCompleted(),
                Timestamp.valueOf(LocalDateTime.now()));
    }

    protected void updateCompletedTask(TaskDto task){
        taskRepository.updateCompleted(task.getId(), task.getTitle(), task.getDetails());
    }
}
