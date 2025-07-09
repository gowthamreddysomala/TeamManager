package com.task.TeamManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Data
@Entity
@Table(name = "tasks")
public class Tasks { // Recommendation: Consider renaming to 'Task' for singular entity naming convention

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "The Title should not be empty")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING) // Store enum as String in DB
    @Column(name = "status", nullable = false)
    // Removed @NotBlank here; use @NotNull for enums if needed
    private ETaskStatus status; // Default value set in constructor

    @Enumerated(EnumType.STRING) // Store enum as String in DB
    @Column(name = "priority", nullable = false)
    // Removed @NotBlank here; use @NotNull for enums if needed
    private ETaskPriority priority; // Default value set in constructor

    @Column(name = "due_date")
    private Date dueDate; // Changed to camelCase

    // --- Connects Task to Project ---
    @ManyToOne(fetch = FetchType.LAZY) // Many tasks can belong to one project
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Task must be associated with a project")
    private Project project; // This holds the Project object the task belongs to

    // --- Relationship to User for assignment ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id", referencedColumnName = "id", nullable = true)
    private User assignedTo; // This holds the User object the task is assigned to

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt; // Changed to camelCase and added nullable=false

    // Constructor to set default enum values
    public Tasks() {
        this.status = ETaskStatus.TO_DO;
        this.priority = ETaskPriority.MEDIUM;
    }
}