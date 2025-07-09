package com.task.TeamManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
// Removed unused imports: jakarta.validation.constraints.NotEmpty, jdk.jfr.Name
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // Placed ID at the top for common convention

    @NotBlank(message = "The username should not be blank")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "The password should not be blank")
    @Column(name = "password", nullable = false) // Corrected: Added nullable = false
    private String password;

    @Column(name = "created_at", updatable = false, nullable = false) // Corrected: Added nullable = false
    @CreationTimestamp
    private LocalDateTime createdAt;
}