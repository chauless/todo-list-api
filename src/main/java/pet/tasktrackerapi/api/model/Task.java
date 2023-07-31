package pet.tasktrackerapi.api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = true)
    private Timestamp completedAt;
}
