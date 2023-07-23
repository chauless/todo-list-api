package pet.tasktrackerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.tasktrackerapi.api.model.Task;
import pet.tasktrackerapi.api.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> getTasksByUser_Id(Long userId);

    void deleteTaskById(UUID taskId);

    boolean existsByUserAndId(User user, UUID taskId);

    int countTasksByUserAndCompletedIsFalse(Long id);
}
