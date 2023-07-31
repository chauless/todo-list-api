package pet.tasktrackerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pet.tasktrackerapi.api.model.Task;
import pet.tasktrackerapi.api.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> getTasksByUser_Id(Long userId);

    void deleteTaskById(UUID taskId);

    boolean existsByUserAndId(User user, UUID taskId);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.title = :title, t.details = :details, t.completed = :completed, t.completedAt = :completedAt WHERE t.id = :id")
    void update(@Param("id") UUID uuid, @Param("title") String title, @Param("details") String details,
                @Param("completed") boolean completed, @Param("completedAt") Timestamp completedAt);


    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.title = :title, t.details = :details WHERE t.id = :id")
    void updateCompleted(@Param("id") UUID uuid, @Param("title") String title, @Param("details") String details);

}
