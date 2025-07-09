package com.task.TeamManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date; // Using java.util.Date as per your previous code for consistency

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "The name field should not be blank")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // Description can be null

    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date must not be null")
    private Date startDate; // Changed to camelCase

    @Column(name = "end_date", nullable = false)
    @NotNull(message = "End date must not be null")
    private Date endDate; // Changed to camelCase

    // Connects Project to User as Project Manager
    @ManyToOne(fetch = FetchType.LAZY) // Many projects can have one manager
    @JoinColumn(name = "project_manager_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Project must have a manager")
    private User projectManager; // This holds the User object who is the manager
}