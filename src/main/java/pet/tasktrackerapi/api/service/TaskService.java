package pet.tasktrackerapi.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Cacheable("tasks")
    public List<TaskDto> getUserTasks(User user) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Task> tasks = taskRepository.getTasksByUser_Id(user.getId());
        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).toList();
    }

    @Caching(evict = {
            @CacheEvict(value="task", allEntries=true),
            @CacheEvict(value="tasks", allEntries=true)})
    public Task createTask(User user, NewTaskRequest newTaskRequest){
        Task newTask = Task
                .builder()
                .title(newTaskRequest.getTitle())
                .details(newTaskRequest.getDetails())
                .completed(false)
                .user(user)
                .build();
        return taskRepository.save(newTask);

        //return modelMapper.map(newTask, TaskDto.class);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value="task", allEntries=true),
            @CacheEvict(value="tasks", allEntries=true)})
    public void deleteTask(User user, Long id){
        if (taskRepository.existsByUserAndId(user, id)){
            taskRepository.deleteTaskById(id);
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value="task", allEntries=true),
            @CacheEvict(value="tasks", allEntries=true)})
    public Task updateTask(User user, TaskDto taskDto) {
        if (!taskRepository.existsByUserAndId(user, taskDto.getId())){
            throw new NotFoundException();
        }

        Task taskToUpdate = taskRepository.findById(taskDto.getId()).get();
        if ((taskDto.getCompleted() && !taskToUpdate.getCompleted())){
            completeTask(taskDto);
        } else if (taskDto.getCompleted()){
            updateCompletedTask(taskDto);
        } else {
            updateUncompletedTask(taskDto);
        }

        return taskRepository.findById(taskDto.getId()).get();

//        List<Task> tasks = taskRepository.getTasksByUser_Id(user.getId());
//        return tasks.stream().map(task-> modelMapper.map(task, TaskDto.class)).toList();
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
