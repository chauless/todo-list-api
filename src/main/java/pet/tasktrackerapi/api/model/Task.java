package pet.tasktrackerapi.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
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

    @ManyToOne
    private User owner;
}
